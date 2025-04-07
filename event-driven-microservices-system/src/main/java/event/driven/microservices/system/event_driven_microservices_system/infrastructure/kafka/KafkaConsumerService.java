package event.driven.microservices.system.event_driven_microservices_system.infrastructure.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumerService {

    @KafkaListener(topics = "test-topic", groupId = "my-group")
    public void consumeMessage(String message) {
        System.out.println("Message received: " + message);
    }
}
