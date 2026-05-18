package cl.factogames.catalogo.event;

import cl.factogames.common.events.VideojuegoEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VideojuegoEventProducer {

    private final KafkaTemplate<String, VideojuegoEvent> kafkaTemplate;

    private static final String TOPIC = "videojuegos-topic";

    public void enviarEvento(VideojuegoEvent event) {
        kafkaTemplate.send(TOPIC, event);
    }
}