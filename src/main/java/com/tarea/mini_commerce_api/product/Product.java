package com.tarea.mini_commerce_api.product;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "products")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String itemName;

    private String description;

    private int price;

    private  int stock;

    private boolean isDeleted = false;

    public void delete() {
        this.isDeleted = true;
    }

    public void update(String itemName, String description, int price, int stock) {
        this.itemName = itemName;
        this.description = description;
        this.price = price;
        this.stock = stock;
    }
}
