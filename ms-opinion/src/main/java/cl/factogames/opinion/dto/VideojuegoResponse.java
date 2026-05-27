package cl.factogames.opinion.dto;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class VideojuegoResponse {

    private String ean;
    private String titulo;
    private BigDecimal precio;
    private String desarrollador;
}
