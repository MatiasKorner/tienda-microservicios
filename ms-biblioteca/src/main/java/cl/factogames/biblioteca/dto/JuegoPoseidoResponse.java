package cl.factogames.biblioteca.dto;

import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JuegoPoseidoResponse {

    private Integer idPropiedad;
    private String email;
    private String ean;
    private LocalDateTime fechaAdquisicion;
    private BigDecimal horasJugadas;
    private List<LicenciaActivaResponse> licencias;
}
