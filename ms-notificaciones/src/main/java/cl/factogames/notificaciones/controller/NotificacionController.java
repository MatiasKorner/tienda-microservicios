package cl.factogames.notificaciones.controller;

import cl.factogames.notificaciones.dto.NotificacionRequest;
import cl.factogames.notificaciones.dto.NotificacionResponse;
import cl.factogames.notificaciones.service.NotificacionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/notificaciones")
@RequiredArgsConstructor
public class NotificacionController {

    private final NotificacionService notificacionService;

    @PostMapping
    public ResponseEntity<NotificacionResponse> enviar(@Valid @RequestBody NotificacionRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(notificacionService.crearNotificacion(request));
    }

    @GetMapping("/usuario/{email}")
    public ResponseEntity<List<NotificacionResponse>> listar(@PathVariable String email) {
        List<NotificacionResponse> response = notificacionService.listarPorUsuario(email);
        return response.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}/leer")
    public ResponseEntity<Void> leer(@PathVariable Long id) {
        notificacionService.marcarComoLeida(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<NotificacionResponse> obtenerPorId(@PathVariable Long id) {
        NotificacionResponse response = notificacionService.obtenerNotificacionPorId(id);
        return ResponseEntity.ok(response);
    }
}
