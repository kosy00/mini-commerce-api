package com.tarea.mini_commerce_api.product;

import com.tarea.mini_commerce_api.product.dto.ProductRequestDto;
import com.tarea.mini_commerce_api.product.dto.ProductResponseDto;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository repository;

    public ProductResponseDto register(ProductRequestDto dto) {
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

    public ProductResponseDto getProduct(Long productId) {
        log.info("상품 단건 조회 시작 - productId={}", productId);
        Product product = repository.findById(productId)
                .orElseThrow(()-> {
                    log.warn("상품 단건 조회 실패 - productId={}", productId);
                    return new ResponseStatusException(HttpStatus.NOT_FOUND,"상품을 찾을 수 없습니다. productId=" + productId);
                        });
        return ProductResponseDto.from(product);
    }

    public List<ProductResponseDto> getAllProducts() {
        log.info("상품 목록 조회 시작");
        return repository.findAll().stream()
                .map(ProductResponseDto::from)
                .toList();
    }
}
