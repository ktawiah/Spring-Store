package dev.practice.springstore.requests;

import dev.practice.springstore.models.Category;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class AddProductRequest {
    private Long id;

    private String name;

    private BigDecimal price;

    private int quantity;

    private int inventory;

    private Category category;

    private String description;

    private String brand;
}
