package cl.factogames.notificaciones.event;

import cl.factogames.notificaciones.service.NotificacionService;
import cl.factogames.common.event.OpinionCreatedEvent;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class NotificacionEventConsumer {

    private final NotificacionService notificacionService;

    /**
     * Escucha el tópico de reseñas creadas.
     * Toma el groupId 'ms-notificaciones' configurado en tu application.yml
     */
    @KafkaListener(topics = "catalogo.opinion.created", groupId = "ms-notificaciones")
    public void escucharOpinionCreada(OpinionCreatedEvent event) {
        log.info(" [Kafka Event] Evento recibido desde ms-opinion. Reseña ID: {}, Usuario ID: {}, Juego ID: {}", 
                event.getIdOpinion(), event.getEmail(), event.getEan());

        // Validaciones defensivas iniciales antes de pasar a la lógica de negocio
        if (event.getEmail() == null) {
            log.warn(" Evento ignorado: El email de usuario viene nulo.");
            return;
        }

        // Delegamos de inmediato el procesamiento al servicio
        notificacionService.procesarNotificacionOpinion(
                event.getEmail(), 
                event.getEan(), 
                event.getCalificacion()
        );
    }
}