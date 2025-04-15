package com.ordersystem.mapper;

import com.ordersystem.dto.OrderDTO;
import com.ordersystem.dto.OrderDetailDTO;
import com.ordersystem.entity.Order;
import com.ordersystem.entity.OrderDetail;

public class OrderMapper {

    public static OrderDTO toDto(Order order) {
        if (order == null) return null;

        OrderDTO dto = new OrderDTO();
        dto.setId(order.getId());
        dto.setUserId(order.getUserId());
        for(OrderDetail orderDetail : order.getOrderDetails()) {
            OrderDetailDTO detailDTO = new OrderDetailDTO();
            detailDTO.setId(orderDetail.getId());
            detailDTO.setProductId(orderDetail.getProductId());
            detailDTO.setQuantity(orderDetail.getQuantity());
            detailDTO.setPrice(orderDetail.getPrice());
            dto.getOrderDetails().add(detailDTO);
        }
        return dto;
    }

    // Optional: nếu sau này cần convert DTO -> Entity
    public static Order toEntity(OrderDTO dto) {
        if (dto == null) return null;

        Order order = new Order();
        order.setId(dto.getId());
        order.setUserId(dto.getUserId());

        return order;
    }
}
