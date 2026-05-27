package cl.factogames.promociones.event;

import cl.factogames.promociones.dto.CuponRequest;
import cl.factogames.promociones.dto.CuponResponse;
import cl.factogames.promociones.service.CampanaService;
import cl.factogames.common.event.OpinionCreatedEvent; 

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Slf4j
@Component
@RequiredArgsConstructor
public class PromocionesEventConsumer {

    private final CampanaService campanaService;

    @KafkaListener(topics = "catalogo.opinion.created", groupId = "ms-promociones")
    public void consumeOpinionCreated(OpinionCreatedEvent event) {
        log.info(" Evento Kafka recibido en ms-promociones -> Reseña ID: {}, Juego ID: {}, Calificación: {}", 
                event.getIdOpinion(), event.getEan(), event.getCalificacion());

        try {
            if (event.getCalificacion() != null && event.getCalificacion() == 5) {
                log.info(" ¡Reseña de 5 estrellas detectada! Generando cupón de recompensa...");

                String codigoCupon = "GRACIAS" + event.getIdOpinion();

                CuponRequest request = CuponRequest.builder()
                        .codigoAlfa(codigoCupon)
                        .montoFijo(new BigDecimal("5000.00"))
                        .usosMaximos(1)
                        .esActivo(true)
                        .build();

                CuponResponse cuponCreado = campanaService.crearCupon(request);
                log.info(" Cupón de recompensa generado: {} por un valor de ${}", 
                        cuponCreado.getCodigoAlfa(), cuponCreado.getMontoFijo());
            }
        } catch (Exception e) {
            log.error(" Error al procesar el beneficio de la reseña en ms-promociones: {}", e.getMessage(), e);
        }
    }
}
