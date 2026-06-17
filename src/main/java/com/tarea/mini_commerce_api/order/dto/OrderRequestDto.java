package com.tarea.mini_commerce_api.order.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record OrderRequestDto(
        @NotNull
        Long productId,

        @Positive
        int quantity
) {
}
