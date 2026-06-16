package com.tarea.mini_commerce_api.product.dto;

import com.tarea.mini_commerce_api.product.Product;

public record ProductResponseDto(
        Long id,
        String itemName,
        String description,
        int price,
        int stock
) {
    public static ProductResponseDto from(Product product) {
        return new ProductResponseDto(
                product.getId(),
                product.getItemName(),
                product.getDescription(),
                product.getPrice(),
                product.getStock()
        );
    }
}
