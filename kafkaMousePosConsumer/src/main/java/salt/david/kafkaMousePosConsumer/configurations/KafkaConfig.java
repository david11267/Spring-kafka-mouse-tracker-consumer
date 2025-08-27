package salt.david.kafkaMousePosConsumer.configurations;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.config.TopicBuilder;

import java.util.Date;

@Configuration
public class KafkaConfig {
    private final ObjectMapper mapper;

    @Bean
    public NewTopic mousePosTopic() {
        return TopicBuilder.name("mousePositionLog")
                .partitions(10)
                .replicas(1)
                .build();
    }

    public KafkaConfig() {
        this.mapper = new ObjectMapper();
        //consider saving data somewhere
    }
    @KafkaListener(id = "mousePosListener", topics = "mousePositionLog")
    public void listen(String in) {
        System.out.println("Hello from Kafka listener!");
        System.out.println("In msg "+in);
    }

    public record TrackingData(String userId, Integer mousePosX, Integer mousePosY, Date regTime) {}

}
