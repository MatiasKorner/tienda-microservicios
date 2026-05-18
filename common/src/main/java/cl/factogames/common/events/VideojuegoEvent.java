package cl.factogames.common.events;

import lombok.*;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VideojuegoEvent {

    private Integer idVideojuego;
    private String titulo;
    private BigDecimal precio;
    private Boolean activo;
    private String tipoEvento;
}