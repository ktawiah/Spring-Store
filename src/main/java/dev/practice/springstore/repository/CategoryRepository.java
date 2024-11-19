package dev.practice.springstore.repository;

import dev.practice.springstore.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Category findByName(String name);
//    public Category findByName(String name);
}
