package com.tarea.mini_commerce_api.product;

import com.tarea.mini_commerce_api.product.dto.ProductRequestDto;
import com.tarea.mini_commerce_api.product.dto.ProductResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
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

    @Transactional(readOnly = true)
    public ProductResponseDto getProduct(Long productId) {
        log.info("상품 단건 조회 시작 - productId={}", productId);
        Product product = repository.findById(productId)
                .orElseThrow(()-> {
                    log.warn("상품 단건 조회 실패 - 해당 상품이 존재하지 않음 - productId={}", productId);
                    return new ResponseStatusException(HttpStatus.NOT_FOUND,"상품을 찾을 수 없습니다. productId=" + productId);
                        });
        log.info("상품 단건 조회 성공 - productId={}", productId);
        return ProductResponseDto.from(product);
    }

    @Transactional(readOnly = true)
    public List<ProductResponseDto> getAllProducts() {
        log.info("상품 목록 조회 시작");
        return repository.findAll().stream()
                .map(ProductResponseDto::from)
                .toList();
    }

    public ProductResponseDto updateProduct(Long productId, ProductRequestDto dto) {
        log.info("상품 수정 시작 - productId={}", productId);
        Product product = repository.findById(productId)
                .orElseThrow(()-> {
                    log.warn("상품 수정 실패 - 해당 상품이 존재하지 않음 - productId={}", productId);
                    return new ResponseStatusException(HttpStatus.NOT_FOUND,"상품을 찾을 수 없습니다. productId=" + productId);
                });
        product.update(dto.itemName(), dto.description(), dto.price(), dto.stock());
        return ProductResponseDto.from(product);
    }

    public void deleteProduct(Long productId) {
        if (!repository.existsById(productId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "상품을 찾을 수 없습니다. productId=" + productId);
        }
        repository.deleteById(productId);
    }
}
