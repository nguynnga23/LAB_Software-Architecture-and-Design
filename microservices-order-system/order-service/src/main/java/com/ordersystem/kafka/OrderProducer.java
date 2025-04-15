package com.ordersystem.kafka;

import com.ordersystem.dto.OrderEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class OrderProducer {
    private final KafkaTemplate<String, OrderEvent> kafkaTemplate;

    public OrderProducer(KafkaTemplate<String, OrderEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendOrderEvent(String topic, OrderEvent event) {
        kafkaTemplate.send(topic, event);
        System.out.println("Order event sent: " + event);
    }
}
