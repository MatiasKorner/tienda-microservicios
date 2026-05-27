package cl.factogames.promociones.dto;

import lombok.*;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CuponResponse {

    private Integer idCupon;
    private String codigoAlfa;
    private BigDecimal montoFijo;
    private Boolean esActivo;
    private Integer usosMaximos;
    
}
