package cl.factogames.pedidos.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.math.BigDecimal;

/**
 * DTO para la creación de nuevos pedidos.
 * Sigue el estilo de validación y estructura de UsuarioRequest.
 */
@Data
public class PedidoRequest {

    @NotNull(message = "El ID de usuario es obligatorio")
    private Integer idUsuario;

    @NotNull(message = "El total de la venta es obligatorio")
    @DecimalMin(value = "0.0", inclusive = true, message = "El total no puede ser negativo")
    private BigDecimal totalVenta;

    @NotNull(message = "El ID del videojuego es obligatorio")
    private Integer idVideojuego;

    @NotNull(message = "La cantidad es obligatoria")
    @Positive(message = "La cantidad debe ser mayor a cero")
    private Integer cantidad;

    // Nota: El estado, la fecha y el código de seguimiento 
    // suelen ser gestionados internamente por el servicio al crear el registro.
}