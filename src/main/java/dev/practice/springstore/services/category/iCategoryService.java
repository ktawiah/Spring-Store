package dev.practice.springstore.services.category;

import dev.practice.springstore.models.Category;

import java.util.List;

public interface iCategoryService {
    Category addCategory(Category category);

    void deleteCategory(Long id);

    Category updateCategory(Category category, Long id);

    List<Category> getAllCategories();

    Category getCategoryById(Long id);

    Category getCategoryByName(String name);
}
