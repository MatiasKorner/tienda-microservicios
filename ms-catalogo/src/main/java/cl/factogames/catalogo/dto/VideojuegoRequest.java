package cl.factogames.catalogo.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class VideojuegoRequest {

    @NotBlank(message = "El título es obligatorio")
    @Size(min = 1, max = 100, message = "El título debe tener entre 1 y 100 caracteres")
    private String titulo;

    @NotNull(message = "El precio es obligatorio")
    @Min(value = 0, message = "El precio no puede ser inferior a 0")
    private BigDecimal precio;

    @NotBlank(message = "La desarrolladora es obligatoria")
    @Size(max = 100, message = "La desarrolladora no puede superar los 100 caracteres")
    private String desarrollador;

}
