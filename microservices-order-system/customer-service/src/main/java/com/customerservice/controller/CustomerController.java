package com.customerservice.controller;

import com.customerservice.clients.OrderServiceClient;
import com.customerservice.clients.UserServiceClient;

import com.customerservice.dto.Order;
import com.customerservice.dto.User;
import com.customerservice.dto.UserOrderResponse;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/customers")

public class CustomerController {
    private final UserServiceClient userServiceClient;
    private final OrderServiceClient orderServiceClient;


    public CustomerController(UserServiceClient userServiceClient, OrderServiceClient orderServiceClient) {
        this.userServiceClient = userServiceClient;
        this.orderServiceClient = orderServiceClient;
    }

    @GetMapping(value = "/user/{id}/details", produces = "application/json")
    @ResponseBody
    public UserOrderResponse getUserDetailsWithOrders(@PathVariable Long id) {
        User user = userServiceClient.getUserById(id);
        List<Order> orders = orderServiceClient.getOrdersByUserId(id);

        if (user == null || orders == null) {
            throw new RuntimeException("User or orders not found");
        }

        return new UserOrderResponse(user, orders);
    }
}