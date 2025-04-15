package com.ordersystem.dto;

import lombok.Data;

@Data
public class Customer {
    private Long id;
    private String fullName;
    private String email;
    private String phone;
    private String address;
}
