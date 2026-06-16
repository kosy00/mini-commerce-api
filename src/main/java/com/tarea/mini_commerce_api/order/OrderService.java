package com.tarea.mini_commerce_api.order;

import com.tarea.mini_commerce_api.order.dto.OrderRequestDto;
import com.tarea.mini_commerce_api.order.dto.OrderResponseDto;
import com.tarea.mini_commerce_api.product.Product;
import com.tarea.mini_commerce_api.product.ProductRepository;

import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    public OrderResponseDto create(OrderRequestDto dto) {
        log.info("주문 생성 시작");
        Product product = productRepository.findByIdForUpdate(dto.productId())
                .orElseThrow(() -> {
                    log.warn("주문 생성 실패 - 주문 대상 상품이 존재하지 않음 - productId={}", dto.productId());
                    return new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 상품을 찾을 수 없습니다. productId=" + dto.productId());
                });

        product.decreaseStock(dto.quantity());

        Order order = Order.builder()
                .product(product)
                .quantity(dto.quantity())
                .totalPrice(product.getPrice() * dto.quantity())
                .orderDate(LocalDateTime.now())
                .build();

        Order saved = orderRepository.save(order);
        log.info("주문 생성 완료 - orderId={}", saved.getId());
        return OrderResponseDto.from(saved);
    }
}
