package com.inventoryservice.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "stock")
public class Stock {
    @Id
    private Long productId;
    private String productName;
    private int quantity;
}
