package com.tarea.mini_commerce_api.product.dto;

public record ProductResponseDto(
        Long id,
        String itemName,
        String description,
        int price,
        int stock
) {
}
