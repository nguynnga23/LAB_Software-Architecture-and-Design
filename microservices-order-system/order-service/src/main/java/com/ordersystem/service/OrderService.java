package com.ordersystem.service;

import com.ordersystem.entity.Order;
import com.ordersystem.entity.OrderDetail;
import com.ordersystem.client.CustomerServiceClient;
import com.ordersystem.client.ProductServiceClient;
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

    public Order createOrder(Order order, List<OrderDetail> orderDetails) {
        // Validate customer existence
        if (customerServiceClient.getCustomerById(order.getUserId()) == null) {
            throw new IllegalArgumentException("Invalid customer ID: " + order.getUserId());
        }

        // Validate each product in the order details
        for (OrderDetail detail : orderDetails) {
            if (productServiceClient.getProductById(detail.getProductId()) == null) {
                throw new IllegalArgumentException("Invalid product ID: " + detail.getProductId());
            }
        }

        // Associate order details with the order
        for (OrderDetail detail : orderDetails) {
            detail.setOrder(order);
        }
        order.setOrderDetails(orderDetails);

        // Save the order and its details
        return orderRepository.save(order);
    }

    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }

    public List<Order> getOrdersByUserId(Long userId) {
        return orderRepository.findByUserId(userId);
    }
}
