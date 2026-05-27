package cl.factogames.carrito.dto;
import lombok.Data;
import java.math.BigDecimal;

@Data
public class VideojuegoProyeccionResponse {

    private String ean;
    private String titulo;
    private BigDecimal precio;
}
