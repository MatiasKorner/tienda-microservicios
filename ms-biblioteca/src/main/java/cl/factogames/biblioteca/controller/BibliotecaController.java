package cl.factogames.biblioteca.controller;

import cl.factogames.biblioteca.dto.*;
import cl.factogames.biblioteca.service.BibliotecaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/v1/biblioteca")
@RequiredArgsConstructor
public class BibliotecaController {

    private final BibliotecaService bibliotecaService;

    @PostMapping("/adquirir")
    public ResponseEntity<JuegoPoseidoResponse> adquirirJuego(@Valid @RequestBody JuegoPoseidoRequest request) {
        return new ResponseEntity<>(bibliotecaService.registrarAdquisicion(request), HttpStatus.CREATED);
    }

    @GetMapping("/usuario/{email}")
    public ResponseEntity<List<JuegoPoseidoResponse>> obtenerBiblioteca(@PathVariable String email) {
        return ResponseEntity.ok(bibliotecaService.obtenerBibliotecaPorUsuario(email));
    }

    @PatchMapping("/propiedad/{idPropiedad}/track-time")
    public ResponseEntity<JuegoPoseidoResponse> registrarTiempo (@PathVariable Integer idPropiedad, @RequestParam BigDecimal horas) {
        return ResponseEntity.ok(bibliotecaService.actualizarHorasJugadas(idPropiedad, horas));
    }

    @PostMapping("/licencias/generar")
    public ResponseEntity<LicenciaActivaResponse> vincularClave(@Valid @RequestBody LicenciaActivaRequest request) {
            return new ResponseEntity<>(bibliotecaService.asociarLicencia(request), HttpStatus.CREATED);
        }

    @PutMapping("/licencia/activar")
    public ResponseEntity<LicenciaActivaResponse> canjearClave(@RequestParam String clave) {
        return ResponseEntity.ok(bibliotecaService.activarLicencia(clave));
    }

    @GetMapping("/plataformas")
    public ResponseEntity<List<PlataformaAccesoResponse>> listarPlataformas() {
        return ResponseEntity.ok(bibliotecaService.listarPlataformas());
    }
}
