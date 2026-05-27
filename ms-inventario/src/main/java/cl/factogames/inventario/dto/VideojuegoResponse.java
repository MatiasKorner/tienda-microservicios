package cl.factogames.inventario.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class VideojuegoResponse {

    private Integer id;
    private String titulo;
    private BigDecimal precio;
    private Boolean activo;
}