package com.ordersystem.controller;

import com.ordersystem.dto.OrderDTO;
import com.ordersystem.dto.OrderRequest;
import com.ordersystem.entity.Order;
import com.ordersystem.entity.OrderDetail;
import com.ordersystem.dto.OrderEvent;
import com.ordersystem.kafka.OrderProducer;
import com.ordersystem.mapper.OrderMapper;
import com.ordersystem.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    @Autowired
    private OrderService orderService;

    private  OrderMapper orderMapper;


    @GetMapping
    public List<OrderDTO> getAllOrders() {
        orderMapper = new OrderMapper();
        List<OrderDTO> orderDTOS = new ArrayList<>();
        for (Order order : orderService.getAllOrders()) {
            OrderDTO orderDTO = orderMapper.toDto(order);
            orderDTOS.add(orderDTO);
        }
        return orderDTOS;
    }

    @GetMapping("/{id}")
    public OrderDTO getOrderById(@PathVariable Long id) {
        OrderDTO orderDTO = orderMapper.toDto(orderService.getOrderById(id));
        return orderDTO;
    }

    @PostMapping
    public OrderDTO createOrder(@RequestBody OrderRequest orderRequest) {
        OrderDTO orderDTO = orderMapper.toDto(orderService.createOrder(orderRequest.getOrder(), orderRequest.getOrderDetails()));
        return orderDTO;
    }

    @DeleteMapping("/{id}")
    public void deleteOrder(@PathVariable Long id) {
        orderService.deleteOrder(id);
    }

    @GetMapping("/user/{userId}")
    public List<OrderDTO> getOrdersByUserId(@PathVariable Long userId) {
        orderMapper = new OrderMapper();
        List<OrderDTO> orderDTOS = new ArrayList<>();
        for (Order order : orderService.getOrdersByUserId(userId)) {
            OrderDTO orderDTO = orderMapper.toDto(order);
            orderDTOS.add(orderDTO);
        }
        return orderDTOS;
    }
}
