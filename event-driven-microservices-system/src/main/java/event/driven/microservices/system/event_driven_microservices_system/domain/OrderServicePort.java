package event.driven.microservices.system.event_driven_microservices_system.domain;

public interface OrderServicePort {
    void placeOrder(Order order);
}