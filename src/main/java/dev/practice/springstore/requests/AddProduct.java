package dev.practice.springstore.requests;

import dev.practice.springstore.models.Category;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class AddProduct {
    @Size(min = 3, max = 255)
    private String name;

    @PositiveOrZero
    private BigDecimal price;

    @PositiveOrZero
    private int quantity;

    @PositiveOrZero
    private int inventory;

    private Category category;

    private String description;

    private String brand;
}
