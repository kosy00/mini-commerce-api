package com.tarea.mini_commerce_api.order.dto;

import com.tarea.mini_commerce_api.order.Order;
import com.tarea.mini_commerce_api.product.dto.ProductResponseDto;

import java.time.LocalDateTime;

public record OrderResponseDto(
        ProductResponseDto product,
        int quantity,
        int totalPrice,
        LocalDateTime orderDate
) {
    public static OrderResponseDto from(Order order) {
        return new OrderResponseDto(
                ProductResponseDto.from(order.getProduct()),
                order.getQuantity(),
                order.getTotalPrice(),
                order.getOrderDate()
        );
    }
}
