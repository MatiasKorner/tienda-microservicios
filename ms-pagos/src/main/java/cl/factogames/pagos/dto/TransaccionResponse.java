package cl.factogames.pagos.dto;

import lombok.*;
import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransaccionResponse {

    private UUID idTransaccion;
    private Integer idPedido;
    private Integer idMetodo;
    private String tipoMetodo;
    private String estado;
    private BigDecimal monto;
}
