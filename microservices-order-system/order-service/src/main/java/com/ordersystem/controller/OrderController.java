package com.ordersystem.controller;

//import com.ordersystem.entity.Order;
import com.ordersystem.dto.Order;
import com.ordersystem.dto.OrderEvent;
import com.ordersystem.kafka.OrderProducer;
import com.ordersystem.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/orders")
public class OrderController {
    @Autowired
    private OrderService orderService;
    private OrderProducer orderProducer;

    public OrderController(OrderProducer orderProducer) {
        this.orderProducer = orderProducer;
    }
    @PostMapping
    public String placeOrder(@RequestBody Order order){

        order.setOrderId(UUID.randomUUID().toString());

        OrderEvent orderEvent = new OrderEvent();
        orderEvent.setStatus("PENDING");
        orderEvent.setMessage("order status is in pending state");
        orderEvent.setOrder(order);

        orderProducer.sendMessage(orderEvent);

        return "Order placed successfully ...";
    }
//    @GetMapping
//    public List<Order> getAllOrders() {
//        return orderService.getAllOrders();
//    }
//
//    @GetMapping("/{id}")
//    public Order getOrderById(@PathVariable Long id) {
//        return orderService.getOrderById(id);
//    }
//
//    @PostMapping
//    public Order createOrder(@RequestBody Order order) {
//        return orderService.createOrder(order);
//    }
//
//    @DeleteMapping("/{id}")
//    public void deleteOrder(@PathVariable Long id) {
//        orderService.deleteOrder(id);
//    }
//
//    @GetMapping("/user/{userId}")
//    public List<Order> getOrdersByUserId(@PathVariable Long userId) {
//        return orderService.getOrdersByUserId(userId);
//    }
}
