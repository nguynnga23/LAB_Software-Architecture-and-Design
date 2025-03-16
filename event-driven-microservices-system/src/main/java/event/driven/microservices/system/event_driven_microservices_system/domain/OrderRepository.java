package event.driven.microservices.system.event_driven_microservices_system.domain;

import java.util.UUID;

public interface OrderRepository {
    void saveOrder(Order order);
    Order findOrderById(UUID id);
}
