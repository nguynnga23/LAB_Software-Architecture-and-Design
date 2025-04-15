package com.ordersystem.dto;

import com.ordersystem.entity.Order;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetailDTO {
    private Long id;
    private Long productId;
    private int quantity;
    private double price;
}
