package cl.factogames.catalogo.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaTopicConfig {

    @Bean
    public NewTopic videojuegosTopic() {
        return new NewTopic("videojuegos-topic", 1, (short) 1);
    }
}