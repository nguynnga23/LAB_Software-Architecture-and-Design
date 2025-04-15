package com.inventoryservice.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class InventoryConsumer {

    @KafkaListener(topics = "order-placed", groupId = "inventory-group")
    public void consumeOrderEvent(String message) {
        System.out.println("Received order event: " + message);
        // Add logic to update inventory
    }
}
