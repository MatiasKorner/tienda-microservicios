package cl.factogames.opinion.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {

    private static final String TOPIC_BASE = "catalogo.opinion";
    
    private static final int DEFAULT_PARTITIONS = 1;
    private static final int DEFAULT_REPLICAS = 1;

    @Bean
    public NewTopic opinionCreatedTopic() {
        return TopicBuilder.name(String.format("%s.created", TOPIC_BASE))
                .partitions(DEFAULT_PARTITIONS)
                .replicas(DEFAULT_REPLICAS)
                .build();
    }

    @Bean
    public NewTopic opinionUpdatedTopic() {
        return TopicBuilder.name(String.format("%s.updated", TOPIC_BASE))
                .partitions(DEFAULT_PARTITIONS)
                .replicas(DEFAULT_REPLICAS)
                .build();
    }

    @Bean
    public NewTopic opinionDeletedTopic() {
        return TopicBuilder.name(String.format("%s.deleted", TOPIC_BASE))
                .partitions(DEFAULT_PARTITIONS)
                .replicas(DEFAULT_REPLICAS)
                .build();
    }
}