package cl.factogames.notificaciones.service;

import cl.factogames.notificaciones.client.UsuarioClient;
import cl.factogames.notificaciones.dto.NotificacionRequest;
import cl.factogames.notificaciones.dto.NotificacionResponse;
import cl.factogames.notificaciones.dto.UsuarioResponse;
import cl.factogames.notificaciones.mapper.NotificacionMapper;
import cl.factogames.notificaciones.model.Notificacion;
import cl.factogames.notificaciones.model.Plantilla;
import cl.factogames.notificaciones.repository.NotificacionRepository;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificacionService {

    private static final Logger log = LoggerFactory.getLogger(NotificacionService.class);

    private final NotificacionRepository notificacionRepository;
    private final NotificacionMapper notificacionMapper;
    private final UsuarioClient usuarioClient;
    private final EntityManager entityManager;

    public void procesarNotificacionOpinion(String email, String ean, Integer calificacion) {
        log.info(" [Kafka Process] Iniciando procesamiento de notificación para Usuario ID: {}", email);

        try {
            UsuarioResponse usuario = usuarioClient.findByEmail(email);
            if (usuario == null || usuario.getEmail() == null) {
                log.warn(" No se encontró al usuario o no posee email válido con ID: {}. Cancelando proceso.", email);
                return;
            }

            Plantilla plantilla = entityManager.createQuery(
                    "SELECT p FROM Plantilla p WHERE p.codigoEvento = :codigo", Plantilla.class)
                    .setParameter("codigo", "BIENVENIDA")
                    .getSingleResult();

            NotificacionRequest requestDto = NotificacionRequest.builder()
                    .email(usuario.getEmail())
                    .idPlantilla(plantilla.getIdPlantilla())
                    .build();
            
            NotificacionResponse response = crearNotificacion(requestDto);
            log.info(" Notificación guardada exitosamente en la DB con ID: {}", response.getIdNotificacion());

            String cuerpoPersonalizado = String.format(
                    plantilla.getCuerpoBase(), 
                    usuario.getNombre(), 
                    ean, 
                    calificacion
            );

            log.info(" ========================================================");
            log.info(" [SMTP OUTBOX] Enviando correo electrónico simulado...");
            log.info(" PARA: {} <{}>", usuario.getNombre() + " " + usuario.getApellido(), usuario.getEmail());
            log.info(" ASUNTO: {}", plantilla.getAsuntoBase());
            log.info(" CUERPO: {}", cuerpoPersonalizado);
            log.info(" ========================================================");

        } catch (Exception e) {
            log.error(" Error crítico al procesar la notificación de reseña: {}", e.getMessage(), e);
        }
    }

    @Transactional
    public NotificacionResponse crearNotificacion(NotificacionRequest request) {
        Notificacion entity = notificacionMapper.toEntity(request);
        return notificacionMapper.toResponse(notificacionRepository.save(entity));
    }

    @Transactional(readOnly = true)
    public List<NotificacionResponse> listarPorUsuario(String email) {
        List<Notificacion> lista = notificacionRepository.findByEmailOrderByFechaEnvioDesc(email);
        return notificacionMapper.toResponseList(lista);
    }

    @Transactional
    public void marcarComoLeida(@NonNull Long idNotificacion) {
        notificacionRepository.findById(idNotificacion).ifPresent(n -> {
            n.setLeido(true);
            notificacionRepository.save(n);
        });
    }

    @Transactional(readOnly = true)
    public NotificacionResponse obtenerNotificacionPorId(Long id) {
        Notificacion notificacion = notificacionRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Notificación no encontrada con ID: " + id));
        return notificacionMapper.toResponse(notificacion);
    }

}
