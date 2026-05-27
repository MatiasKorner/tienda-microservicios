package cl.factogames.promociones.dto;

import lombok.*;
import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CampanaResponse {

    private Integer idCampana;
    private String nombre;
    private Integer descuentoPorcentaje;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private Boolean estaVigente;
    private List<String> juegosAsociados;

}
