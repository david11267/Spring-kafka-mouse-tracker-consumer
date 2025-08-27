package salt.david.kafkaMousePosProducer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping("api/mousePosition")
public class MousePosController {

    private final KafkaTemplate<String, String> template;
    private final ObjectMapper mapper;

    public MousePosController(KafkaTemplate<String, String> template) {
        this.template = template;
        mapper = new ObjectMapper();
    }

    @PostMapping
    public void postMousePos(@RequestBody TrackingData mouseLog) throws JsonProcessingException {
        // do other stuff
        final String json = mapper.writeValueAsString(mouseLog);
        template.send("mousePositionLog", mouseLog.userId, json);
    }

    public record TrackingData(String userId, Integer mousePosX, Integer mousePosY, Date regTime) {}
}
