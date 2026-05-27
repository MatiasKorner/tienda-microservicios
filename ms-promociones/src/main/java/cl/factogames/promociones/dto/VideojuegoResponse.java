package cl.factogames.promociones.dto;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class VideojuegoResponse {

    private Long id;
    private String titulo;
    private BigDecimal precio;
    private String desarrollador;
}