package cl.factogames.pagos.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class PedidoResponse {

    private Integer id;
    private Integer idUsuario;
    private String estado;
    private LocalDateTime fechaPedido;
    private BigDecimal totalVenta;
    private String codigoSeguimiento;
}