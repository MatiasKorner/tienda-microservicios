package cl.factogames.carrito.event;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import cl.factogames.carrito.dto.VideojuegoProyeccionRequest;
import cl.factogames.carrito.service.CarritoService;
import cl.factogames.common.event.VideojuegoCreatedEvent;
import cl.factogames.common.event.VideojuegoDeletedEvent;
import cl.factogames.common.event.VideojuegoUpdatedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class VideojuegoEventConsumer {

    private final CarritoService carritoService;

    @KafkaListener(topics = "catalogo.videojuego.created", groupId = "ms-carrito", 
        properties = {"spring.json.value.default.type=cl.factogames.common.event.VideojuegoCreatedEvent"}
    )
    public void onVideojuegoCreated(ConsumerRecord<String, VideojuegoCreatedEvent> record) {
        VideojuegoCreatedEvent event = record.value();

        log.debug("********************");
        log.debug("Evento recibido → topic: {}, key: {}", record.topic(), record.key());
        log.debug("********************");

        VideojuegoProyeccionRequest request = new VideojuegoProyeccionRequest();
        request.setEan(event.getEan());
        request.setTitulo(event.getTitulo());
        request.setPrecio(event.getPrecio());

        carritoService.sincronizarVideojuego(request);
    }

    @KafkaListener(topics = "catalogo.videojuego.updated", groupId = "ms-carrito",
        properties = {"spring.json.value.default.type=cl.factogames.common.event.VideojuegoUpdatedEvent"}
    )
    public void onVideojuegoUpdated(ConsumerRecord<String, VideojuegoUpdatedEvent> record) {
        VideojuegoUpdatedEvent event = record.value();

        log.debug("********************");
        log.debug("Evento recibido → topic: {}, key: {}", record.topic(), record.key());
        log.debug("********************");

        VideojuegoProyeccionRequest request = new VideojuegoProyeccionRequest();
        request.setEan(event.getEan());
        request.setTitulo(event.getTitulo());
        request.setPrecio(event.getPrecio());

        carritoService.sincronizarVideojuego(request);
    }

    @KafkaListener(topics = "catalogo.videojuego.deleted", groupId = "ms-carrito",
        properties = {"spring.json.value.default.type=cl.factogames.common.event.VideojuegoDeletedEvent"}
    )
    public void onVideojuegoDeleted(ConsumerRecord<String, VideojuegoDeletedEvent> record) {
        VideojuegoDeletedEvent event = record.value();

        log.debug("********************");
        log.debug("Evento recibido → topic: {}, key: {}", record.topic(), record.key());
        log.debug("********************");

        // El videojuego eliminado en catálogo no se borra de la proyección local
        // porque los carritos existentes pueden tener ítems con ese EAN como historial
        log.warn("Videojuego eliminado en catálogo → EAN: {}. "
            + "Se mantiene en proyección local por integridad de carritos existentes.", 
            event.getEan());
    }
}