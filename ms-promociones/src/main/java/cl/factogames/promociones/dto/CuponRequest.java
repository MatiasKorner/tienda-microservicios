package cl.factogames.promociones.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CuponRequest {

    @NotBlank(message = "El código alfa es obligatorio")
    @Size(max = 15, message = "El código no puede superar los 15 caracteres")
    private String codigoAlfa;

    @NotNull(message = "El monto fijo es obligatorio")
    @DecimalMin(value = "0.0", inclusive = false, message = "El monto debe ser mayor a 0")
    private BigDecimal montoFijo;

    @NotNull(message = "Los usos máximos son obligatorios")
    @Min(value = 1, message = "Los usos máximos deben ser al menos 1")
    @Builder.Default
    private Integer usosMaximos = 1;

    @NotNull(message = "El estado del cupón es obligatorio")
    @Builder.Default
    private Boolean esActivo = true;
}
