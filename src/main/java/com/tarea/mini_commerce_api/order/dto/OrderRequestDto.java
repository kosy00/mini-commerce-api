package com.tarea.mini_commerce_api.order.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;

public record OrderRequestDto(
        @NotBlank
        Long productId,

        @PositiveOrZero
        int quantity
) {
}
