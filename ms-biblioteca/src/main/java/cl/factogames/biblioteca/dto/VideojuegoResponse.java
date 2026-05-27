package cl.factogames.biblioteca.dto;

import java.math.BigDecimal;

import lombok.*;

@Data
public class VideojuegoResponse {

    private String ean;
    private String titulo;
    private BigDecimal precio;
}
