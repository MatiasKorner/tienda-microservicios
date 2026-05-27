package cl.factogames.notificaciones.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NotificacionResponse {

    private Long idNotificacion;
    private String email;
    private String asuntoBase;
    private String cuerpoBase;
    private String nombreCanal;
    private Boolean leido;
    private LocalDateTime fechaEnvio;

    private NotificacionRequest detalleNotificacion;
}
