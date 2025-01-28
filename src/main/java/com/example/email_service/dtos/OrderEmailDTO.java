package com.example.email_service.dtos;

import java.util.List;

public class OrderEmailDTO {
    private Long userId;
    private List<OrderItemEmailDTO> orderItems;
    private Double total;

    public OrderEmailDTO(Long userId, List<OrderItemEmailDTO> orderItems, Double total) {
        this.userId = userId;
        this.orderItems = orderItems;
        this.total = total;
    }

    public Long getUserId() {
        return userId;
    }

    public List<OrderItemEmailDTO> getOrderItems() {
        return orderItems;
    }

    public Double getTotal() {
        return total;
    }

    public static class OrderItemEmailDTO {
        private String productName;
        private Double productPrice;
        private Integer quantity;

        public OrderItemEmailDTO(String productName, Double productPrice, Integer quantity) {
            this.productName = productName;
            this.productPrice = productPrice;
            this.quantity = quantity;
        }

        public String getProductName() {
            return productName;
        }

        public Double getProductPrice() {
            return productPrice;
        }

        public Integer getQuantity() {
            return quantity;
        }
    }
}
