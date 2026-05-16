package cl.factogames.pagos.dto;

import lombok.*;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransaccionRequest {

    private Integer idPedido;
    private Integer idMetodo;
    private BigDecimal monto;
}