package salt.david.kafkaMousePosConsumer.configurations;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaListener;

import java.util.Date;

@Configuration
public class KafkaConfig {
    private final ObjectMapper mapper;

    public KafkaConfig() {
        this.mapper = new ObjectMapper();
        //consider saving data somewhere
    }
    @KafkaListener(id = "mousePosListener", topics = "test-topic")
    public void listen(String in) {
        System.out.println("Hello from Kafka listener!");
        System.out.println("In msg "+in);


    }

    public record TrackingData(String userId, Integer mousePosX, Integer mousePosY, Date regTime) {}

}
