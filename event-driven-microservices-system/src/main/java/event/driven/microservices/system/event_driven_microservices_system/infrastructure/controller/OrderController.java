package event.driven.microservices.system.event_driven_microservices_system.infrastructure.controller;

import event.driven.microservices.system.event_driven_microservices_system.application.service.OrderService;
import event.driven.microservices.system.event_driven_microservices_system.domain.model.Order;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public String placeOrder(@RequestBody Order order) {
        orderService.placeOrder(order);
        return "Order placed successfully!";
    }
}
