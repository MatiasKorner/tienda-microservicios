package cl.factogames.common.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VideojuegoCreatedEvent implements VideojuegoEvent {

    private String ean;
    private String titulo;
    private BigDecimal precio;

}