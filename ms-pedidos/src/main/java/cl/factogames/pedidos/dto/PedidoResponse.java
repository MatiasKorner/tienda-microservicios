package cl.factogames.pedidos.dto;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Data;

@Data
public class PedidoResponse {

    private Integer id;
    private Integer idUsuario;
    private String estado;
    private LocalDateTime fechaPedido;
    private BigDecimal totalVenta;
    private String codigoSeguimiento;

}
