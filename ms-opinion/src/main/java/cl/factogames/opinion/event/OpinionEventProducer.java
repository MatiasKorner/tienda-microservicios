package cl.factogames.opinion.event;

import cl.factogames.common.event.OpinionEvent;
import cl.factogames.common.event.OpinionCreatedEvent;
import cl.factogames.common.event.OpinionUpdateEvent;
import cl.factogames.common.event.OpinionDeletedEvent;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class OpinionEventProducer {

    private static final String TOPIC_BASE = "catalogo.opinion";
    private static final String ID_OPINION_NOT_NULL = "El ID de la opinion no puede ser null";
    private static final String TOPIC_NOT_NULL = "El topic no puede ser null";

    private final KafkaTemplate<String, OpinionEvent> kafkaTemplate;

    private void send(OpinionEvent event, String eventType) {

        String topic = Objects.requireNonNull(String.format("%s.%s", TOPIC_BASE, eventType), TOPIC_NOT_NULL);

        String opinionId = Objects.requireNonNull(String.valueOf(event.getIdOpinion()), ID_OPINION_NOT_NULL);

        log.debug("********************");
        log.debug("Enviando evento Kafka → topic: {}, key/idOpinion: {}", topic, opinionId);
        log.debug("********************");

        kafkaTemplate.send(topic, opinionId, event);
    }

    public void sendCreated(OpinionCreatedEvent event) {
        send(event, "created");
    }

    public void sendUpdate(OpinionUpdateEvent event) {
        send(event, "updated");
    }

    public void sendDeleted(OpinionDeletedEvent event) {
        send(event, "deleted");
    }
}
