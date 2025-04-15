package com.inventoryservice.client;

import com.inventoryservice.dto.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "product-service", url = "http://localhost:8081/api/products")
public interface ProductServiceClient {
    @GetMapping("/{id}")
    Product getProductById(@PathVariable("id") Long id);
}
