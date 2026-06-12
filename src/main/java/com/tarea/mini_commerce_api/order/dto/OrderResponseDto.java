package com.tarea.mini_commerce_api.order.dto;

import com.tarea.mini_commerce_api.product.Product;

import java.time.LocalDateTime;

public record OrderResponseDto(
        Product product,
        int quantity,
        int totalPrice,
        LocalDateTime orderDate
) {
}
