package event.driven.microservices.system.event_driven_microservices_system.infrastructure.controller;

import event.driven.microservices.system.event_driven_microservices_system.infrastructure.kafka.KafkaProducerService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/kafka")
public class KafkaController {

    private final KafkaProducerService kafkaProducerService;

    public KafkaController(KafkaProducerService kafkaProducerService) {
        this.kafkaProducerService = kafkaProducerService;
    }

    @PostMapping("/send")
    public String sendMessage(@RequestParam String message) {
        kafkaProducerService.sendMessage("test-topic", message);
        return "Message sent: " + message;
    }
}
