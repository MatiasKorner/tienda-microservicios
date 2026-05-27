package cl.factogames.catalogo.dto;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class VideojuegoResponse {

    private Long id;
    private String ean;
    private String titulo;
    private BigDecimal precio;
    private String desarrollador;
}
