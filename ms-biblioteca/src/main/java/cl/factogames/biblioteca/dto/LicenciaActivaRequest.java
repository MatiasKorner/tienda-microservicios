package cl.factogames.biblioteca.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LicenciaActivaRequest {

    @NotNull(message = "El ID de la propiedad del juego es obligatorio")
    private Integer idPropiedad;

    @NotNull(message = "El ID de la plataforma es obligatorio")
    private Integer idPlataforma;

    @NotBlank(message = "La clave de activación es obligatoria")
    @Size(max = 50, message = "La clave no puede superar los 50 caracteres")
    private String claveActivacion;
}
