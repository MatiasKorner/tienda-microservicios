package cl.factogames.promociones.dto;

import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CampanaRequest {

    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    @Min(1) @Max(99)
    @NotNull(message = "El porcentaje es obligatorio")
    private Integer descuentoPorcentaje;

    @NotNull(message = "La fecha de inicio es obligatoria")
    private LocalDate fechaInicio;

    @NotNull(message = "La fecha de fin es obligatoria")
    private LocalDate fechaFin;

    private List<String> eansJuegos;

    public boolean tieneFechasValidas() {
        if (this.fechaInicio == null || this.fechaFin == null) return false;
        return this.fechaFin.isAfter(this.fechaInicio);
    }
}
