package cl.factogames.pedidos.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.math.BigDecimal;

/**
 * DTO para la creación de nuevos pedidos.
 * Sigue el estilo de validación y estructura de UsuarioRequest.
 */
@Data
public class PedidoRequest {

    @NotBlank(message = "El email del usuario es obligatorio")
    @Email(message = "Debe proporcionar un formato de email válido")
    @Size(max = 100, message = "El email no puede superar los 100 caracteres")
    private String email;

    @NotNull(message = "El total de la venta es obligatorio")
    @DecimalMin(value = "0.0", inclusive = true, message = "El total no puede ser negativo")
    private BigDecimal totalVenta;

    @NotBlank(message = "El EAN es obligatorio")
    @Pattern(
        regexp = "^[0-9]{13}$",
        message = "El EAN debe tener formato EAN-13 válido (ej: 3391892017250)"
    )
    private String ean;

    @NotNull(message = "La cantidad es obligatoria")
    @Positive(message = "La cantidad debe ser mayor a cero")
    private Integer cantidad;

    // Nota: El estado, la fecha y el código de seguimiento 
    // suelen ser gestionados internamente por el servicio al crear el registro.
}