package com.ordersystem.dto;

import com.ordersystem.entity.Order;
import com.ordersystem.entity.OrderDetail;
import lombok.Data;

import java.util.List;

@Data
public class OrderRequest {
    private Order order;
    private List<OrderDetail> orderDetails;
}
