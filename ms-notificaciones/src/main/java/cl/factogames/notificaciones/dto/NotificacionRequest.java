package cl.factogames.notificaciones.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NotificacionRequest {

    @NotNull(message = "El email del usuario es obligatorio")
    @Email(message = "Debe ser un formato de email válido")
    private String email;

    @NotNull(message = "El idPlantilla es obligatorio")
    private Integer idPlantilla;
}
