package cl.factogames.inventario.event;

import cl.factogames.common.events.VideojuegoEvent;
import cl.factogames.inventario.model.VideojuegoProyeccion;
import cl.factogames.inventario.repository.VideojuegoProyeccionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VideojuegoEventConsumer {

    private final VideojuegoProyeccionRepository videojuegoProyeccionRepository;

    @KafkaListener(topics = "videojuegos-topic", groupId = "factogames-group")
    public void consumirEvento(VideojuegoEvent event) {

        String id = String.valueOf(event.getIdVideojuego());

        if ("DELETED".equals(event.getTipoEvento())) {
            videojuegoProyeccionRepository.deleteById(id);
            return;
        }

        VideojuegoProyeccion proyeccion = videojuegoProyeccionRepository
                .findById(id)
                .orElse(new VideojuegoProyeccion());

        proyeccion.setId(id);
        proyeccion.setTitulo(event.getTitulo());

        videojuegoProyeccionRepository.save(proyeccion);
    }
}