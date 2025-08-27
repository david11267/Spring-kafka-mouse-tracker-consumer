package configurations;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.tools.jconsole.JConsoleContext;
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
    @KafkaListener(id = "myId", topics = "mousePosLogs")
    public void listen(ConsumerRecord<String, String> record) {
        System.out.println("Hello from Kafka listener!");
        // injecting ConsumerRecord in case we need message metadata
        // otherwise can just inject a String
        String value = record.value();

        //Replace with mouse pos and user id
        try {
            TrackingData mouseData = mapper.readValue(value, TrackingData.class);
            System.out.println("mouseData = " + mouseData);

            //save mouse data here
        } catch (JsonProcessingException e) {
            System.out.println("EXCEPTION DESERIALIZING MESSAGE!");
            e.printStackTrace();
        }
    }

    public record TrackingData(String userId, Integer mousePosX, Integer mousePosY, Date regTime) {}

}
