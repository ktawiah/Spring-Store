package dev.practice.springstore.repository;

import dev.practice.springstore.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByCategoryName(String category);

    List<Product> findByBrand(String brand);

    List<Product> findByName(String name);

    List<Product> findByCategoryNameAndBrand(String category, String brand);

    List<Product> findByBrandAndName(String brand, String name);

    List<Product> findByCategoryNameAndName(String category, String name);

    Long countByBrandAndName(String brand, String name);
}
