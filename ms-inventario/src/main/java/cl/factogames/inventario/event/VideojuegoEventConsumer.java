package cl.factogames.inventario.event;

import cl.factogames.common.event.VideojuegoCreatedEvent;
import cl.factogames.inventario.model.VideojuegoProyeccion;
import cl.factogames.inventario.repository.VideojuegoProyeccionRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VideojuegoEventConsumer {

    private final VideojuegoProyeccionRepository videojuegoProyeccionRepository;
    private final ObjectMapper objectMapper;

    @KafkaListener(topics = "videojuegos-topic", groupId = "factogames-group")
    public void consumirEvento(String mensaje) throws Exception {

        VideojuegoCreatedEvent event =
                objectMapper.readValue(mensaje, VideojuegoCreatedEvent.class);

        String ean = event.getEan();

        VideojuegoProyeccion proyeccion = videojuegoProyeccionRepository
                .findById(ean)
                .orElse(new VideojuegoProyeccion());

        proyeccion.setEan(ean);
        proyeccion.setTitulo(event.getTitulo());

        videojuegoProyeccionRepository.save(proyeccion);
    }
}