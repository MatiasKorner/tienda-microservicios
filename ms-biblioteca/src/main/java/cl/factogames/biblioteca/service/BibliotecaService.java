package cl.factogames.biblioteca.service;

import cl.factogames.biblioteca.client.CatalogoClient;
import cl.factogames.biblioteca.client.UsuarioClient;
import cl.factogames.biblioteca.dto.*;
import cl.factogames.biblioteca.model.JuegoPoseido;
import cl.factogames.biblioteca.model.LicenciaActiva;
import cl.factogames.biblioteca.model.PlataformaAcceso;
import cl.factogames.biblioteca.mapper.BibliotecaMapper;
import cl.factogames.biblioteca.repository.JuegoPoseidoRepository;
import cl.factogames.biblioteca.repository.LicenciaActivaRepository;
import cl.factogames.biblioteca.repository.PlataformaAccesoRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BibliotecaService {

    private final JuegoPoseidoRepository juegoRepository;
    private final LicenciaActivaRepository licenciaRepository;
    private final PlataformaAccesoRepository plataformaRepository;
    private final BibliotecaMapper mapper;

    private final UsuarioClient usuarioClient;
    private final CatalogoClient catalogoClient;

    @Transactional
    public JuegoPoseidoResponse registrarAdquisicion(JuegoPoseidoRequest request) {
        juegoRepository.findByEmailAndEan(request.getEmail(), request.getEan())
            .ifPresent(j -> {
                throw new RuntimeException("El usuario ya posee este videojuego en su biblioteca.");
            });

        usuarioClient.obtenUsuarioPorEmail(request.getEmail());
        catalogoClient.obtenerVideojuegoPorEan(request.getEan());

        JuegoPoseido juego = mapper.toEntity(request);
        juego.setHorasJugadas(BigDecimal.ZERO);
        return mapper.toResponse(juegoRepository.save(juego));
    }

    public List<JuegoPoseidoResponse> obtenerBibliotecaPorUsuario(String email) {
        return mapper.toJuegoPoseidoResponseList(juegoRepository.findByEmail(email));
    }

    @Transactional
    public JuegoPoseidoResponse actualizarHorasJugadas(Integer idPropiedad, BigDecimal horas) {
        if (horas == null || horas.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Las horas a sumar deben ser un valor positivo.");
        }

        JuegoPoseido juego = juegoRepository.findById(idPropiedad)
            .orElseThrow(() -> new RuntimeException("Propiedad de juego no encontrada con ID: " + idPropiedad));

        juego.setHorasJugadas(juego.getHorasJugadas().add(horas));
        return mapper.toResponse(juegoRepository.save(juego));
    }

    @Transactional
    public LicenciaActivaResponse asociarLicencia(LicenciaActivaRequest request) {
        JuegoPoseido juego = juegoRepository.findById(request.getIdPropiedad())
            .orElseThrow(() -> new RuntimeException("Juego poseído no encontrado con ID: " + request.getIdPropiedad()));
        PlataformaAcceso plataforma = plataformaRepository.findById(request.getIdPlataforma())
            .orElseThrow(() -> new RuntimeException("Plataforma de acceso no encontrada con ID: " + request.getIdPlataforma()));

        licenciaRepository.findByClaveActivacion(request.getClaveActivacion()).ifPresent(l -> {
            throw new RuntimeException("Esta clave de activación ya se encuentra registrada en el sistema.");
        });

        LicenciaActiva licencia = LicenciaActiva.builder().juegoPoseido(juego).plataforma(plataforma)
            .claveActivacion(request.getClaveActivacion()).build();

        return mapper.toResponse(licenciaRepository.save(licencia));
    }

    @Transactional
    public LicenciaActivaResponse activarLicencia(String clave) {
        LicenciaActiva licencia = licenciaRepository.findByClaveActivacion(clave)
            .orElseThrow(() -> new RuntimeException("La clave de activacion ingresada es inválida o no existe."));

        if (licencia.getActivadaEn() != null) {
            throw new RuntimeException("Esta licencia ya fue canjeada previamente el: " + licencia.getActivadaEn());
        }

        licencia.setActivadaEn(LocalDateTime.now());
        return mapper.toResponse(licenciaRepository.save(licencia));
    }

    public List<PlataformaAccesoResponse> listarPlataformas() {
        return mapper.toPlataformaResponseList(plataformaRepository.findAll());
    }
}
