package cl.factogames.opinion.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.Email;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OpinionRequest {

    @NotNull(message = "El EAN del juego es obligatorio")
    private String ean;

    @NotBlank(message = "El email del usuario es obligatorio")
    @Email(message = "Debe proporcionar un formato de email válido")
    private String email;

    @Min(value = 1, message = "La calificación mínima es 1")
    @Max(value = 5, message = "La calificación máxima es 5")
    @NotNull(message = "La calificación es obligatoria")
    private Integer calificacion;

    @NotBlank(message = "El comentario no puede estar vacío")
    private String comentario;

    private Integer idEstado;
}
