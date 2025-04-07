package event.driven.microservices.system.event_driven_microservices_system.application.service;

import event.driven.microservices.system.event_driven_microservices_system.domain.model.Order;
import event.driven.microservices.system.event_driven_microservices_system.domain.repository.OrderRepository;
import event.driven.microservices.system.event_driven_microservices_system.application.ports.OrderServicePort;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
//@RequiredArgsConstructor
public class OrderService implements OrderServicePort {
    private final OrderRepository orderRepository;
    private final KafkaTemplate<String, String> kafkaTemplate;

    public OrderService(OrderRepository orderRepository, KafkaTemplate<String, String> kafkaTemplate) {
        this.orderRepository = orderRepository;
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void placeOrder(Order order) {
        order.setId(UUID.randomUUID());
        order.setStatus("PENDING");
        orderRepository.saveOrder(order);
        kafkaTemplate.send("order-placed", "Order ID: " + order.getId());
    }
}