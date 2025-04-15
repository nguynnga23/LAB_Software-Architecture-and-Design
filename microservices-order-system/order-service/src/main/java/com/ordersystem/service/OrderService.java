package com.ordersystem.service;

import com.ordersystem.entity.Order;
import com.ordersystem.entity.OrderDetail;
import com.ordersystem.feign.CustomerServiceClient;
import com.ordersystem.feign.ProductServiceClient;
import com.ordersystem.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CustomerServiceClient customerServiceClient;

    @Autowired
    private ProductServiceClient productServiceClient;

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Order getOrderById(Long id) {
        return orderRepository.findById(id).orElse(null);
    }

    public Order createOrder(Order order) {
        // Validate customer
        customerServiceClient.getCustomerById(order.getUserId());

        // Validate product
        productServiceClient.getProductById(Long.valueOf(order.getProduct()));

        return orderRepository.save(order);
    }

    public Order createOrder(Order order, List<OrderDetail> orderDetails) {
        // Validate customer and products (reuse Feign clients)
        customerServiceClient.getCustomerById(order.getUserId());
        for (OrderDetail detail : orderDetails) {
            productServiceClient.getProductById(detail.getProductId());
        }

        // Save order and details
        order.setOrderDetails(orderDetails);
        for (OrderDetail detail : orderDetails) {
            detail.setOrder(order);
        }
        return orderRepository.save(order);
    }

    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }

    public List<Order> getOrdersByUserId(Long userId) {
        return orderRepository.findByUserId(userId);
    }
}
