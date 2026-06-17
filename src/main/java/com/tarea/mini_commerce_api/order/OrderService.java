package com.tarea.mini_commerce_api.order;

import com.tarea.mini_commerce_api.order.dto.OrderRequestDto;
import com.tarea.mini_commerce_api.order.dto.OrderResponseDto;
import com.tarea.mini_commerce_api.product.Product;
import com.tarea.mini_commerce_api.product.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

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

    @Transactional(readOnly = true)
    public OrderResponseDto getOrder(Long orderId) {
        log.info("주문 단건 조회 시작 - orderId = {}", orderId);
        Order order = orderRepository.findById(orderId)
                .orElseThrow(()-> {
                    log.warn("주문 단건 조회 실패 - 해당 주문이 존재하지 않음 - orderId = {}", orderId);
                    return new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 주문을 찾을 수 없습니다. orderId=" + orderId);
                });
        log.info("주문 단건 조회 완료 - orderId = {}", orderId);
        return OrderResponseDto.from(order);
    }

    @Transactional(readOnly = true)
    public Page<OrderResponseDto> getOrders(Pageable pageable) {
        log.info("주문 목록 조회 시작 - page={}, size={}", pageable.getPageNumber(), pageable.getPageSize());
        return orderRepository.findAll(pageable)
                .map(OrderResponseDto::from);
    }
}
