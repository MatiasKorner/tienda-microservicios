package cl.factogames.carrito.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class ItemCarritoRequest {

    @NotBlank(message = "El EAN es obligatorio")
    @Pattern(
        regexp = "/^[0-9]{13}$",
        message = "El EAN debe tener formato EAN-13 válido (ej: 3391892017250)"
    )
    private String ean;

    @NotNull(message = "La cantidad es obligatoria")
    @Min(value = 1, message = "La cantidad mínima es 1")
    private Integer cantidad;
}