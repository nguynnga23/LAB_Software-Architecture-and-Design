package com.customerservice.clients;

import com.customerservice.dto.Order;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "order-service", url = "http://localhost:8082")
public interface OrderServiceClient {
    @GetMapping("/orders/user/{userId}")
    List<Order> getOrdersByUserId(@PathVariable("userId") Long userId);
}
