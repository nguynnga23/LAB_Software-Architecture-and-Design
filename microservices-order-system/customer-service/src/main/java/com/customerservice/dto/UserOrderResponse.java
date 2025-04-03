package com.customerservice.dto;

import java.util.List;

public class UserOrderResponse {
    private User user;
    private List<Order> orders;

    public UserOrderResponse(User user, List<Order> orders) {
        this.user = user;
        this.orders = orders;
    }

    public UserOrderResponse() {
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    @Override
    public String toString() {
        return "UserOrderResponse{" +
                "user=" + user +
                ", orders=" + orders +
                '}';
    }
}
