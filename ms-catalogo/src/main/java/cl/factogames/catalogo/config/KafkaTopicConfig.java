package cl.factogames.catalogo.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
public class KafkaTopicConfig {
    
    @Bean
    public NewTopic topicVideojuegoCreated() {
        log.debug("********************");
        log.debug("********************");
        log.debug("********************");
        log.debug("Publicado topic Kafka → topic: {}", "catalogo.videojuego.created");
        log.debug("********************");
        log.debug("********************");
        log.debug("********************");
        return TopicBuilder.name("catalogo.videojuego.created")
                .partitions(1) // En desarrollo con 1 está bien
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic topicVideojuegoUpdated() {
        log.debug("********************");
        log.debug("********************");
        log.debug("********************");
        log.debug("Publicado topic Kafka → topic: {}", "catalogo.videojuego.updated");
        log.debug("********************");
        log.debug("********************");
        log.debug("********************");
        return TopicBuilder.name("catalogo.videojuego.updated")
                .partitions(1)
                .build();
    }

    @Bean
    public NewTopic topicVideojuegoDeleted() {
        log.debug("********************");
        log.debug("********************");
        log.debug("********************");
        log.debug("Publicado topic Kafka → topic: {}", "catalogo.videojuego.deleted");
        log.debug("********************");
        log.debug("********************");
        log.debug("********************");
        return TopicBuilder.name("catalogo.videojuego.deleted")
                .partitions(1)
                .build();
    }
}
