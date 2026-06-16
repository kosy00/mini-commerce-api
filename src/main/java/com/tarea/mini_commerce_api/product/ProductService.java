package com.tarea.mini_commerce_api.product;

import com.tarea.mini_commerce_api.product.dto.ProductRequestDto;
import com.tarea.mini_commerce_api.product.dto.ProductResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository repository;

    public ProductResponseDto register (Long productId, ProductRequestDto dto ) {
        log.info("상품 등록 시작 - productName ={}", dto.itemName());
        Product product = Product.builder()
                .itemName(dto.itemName())
                .description(dto.description())
                .price(dto.price())
                .stock(dto.stock())
                .build();

        Product saved = repository.save(product);
        log.info("상품 등록 완료 - productName ={}", dto.itemName());
        return ProductResponseDto.from(saved);
    }
}
