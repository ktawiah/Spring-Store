package dev.practice.springstore.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Product {
    public Product(String name, BigDecimal price, int quantity, int inventory, Category category, String description, String brand) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.inventory = inventory;
        this.category = category;
        this.description = description;
        this.brand = brand;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(min = 3, max = 255)
    private String name;

    @PositiveOrZero
    private BigDecimal price;

    @PositiveOrZero
    private int quantity;

    @PositiveOrZero
    private int inventory;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "category_id")
    private Category category;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Image> images;

    private String description;

    private String brand;

    public Product(@Size(min = 3, max = 255) String name, @PositiveOrZero BigDecimal price, String brand, String description, @PositiveOrZero int inventory, Category category) {
    }
}
