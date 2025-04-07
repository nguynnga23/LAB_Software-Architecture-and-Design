package event.driven.microservices.system.event_driven_microservices_system.application.ports;

import event.driven.microservices.system.event_driven_microservices_system.domain.model.Order;

public interface OrderServicePort {
    void placeOrder(Order order);
}