package com.tarea.mini_commerce_api.product;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {

    Optional<Product> findByIdAndIsDeletedFalse(Long id);

    List<Product> findAllByIsDeletedFalse();
}
