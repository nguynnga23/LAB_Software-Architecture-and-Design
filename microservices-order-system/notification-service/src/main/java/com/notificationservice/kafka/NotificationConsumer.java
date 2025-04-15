package com.notificationservice.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class NotificationConsumer {

    @KafkaListener(topics = "order-placed", groupId = "notification-group")
    public void consumeOrderEvent(String message) {
        System.out.println("Received order event: " + message);
        // Add logic to send email notification
    }
}
