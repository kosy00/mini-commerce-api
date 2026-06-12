package com.tarea.mini_commerce_api.product.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;

public record ProductRequestDto(
        @NotBlank
        String itemName,

        String description,

        @PositiveOrZero
        int price,

        @PositiveOrZero
        int stock
) {
}