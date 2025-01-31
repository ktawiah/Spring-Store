package dev.practice.springstore.services.category;

import dev.practice.springstore.exceptions.CategoryNotFoundException;
import dev.practice.springstore.models.product.Category;
import dev.practice.springstore.repository.CategoryRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryService implements iCategoryService{

    final private CategoryRepository repository;
    private final CategoryRepository categoryRepository;

    @Transactional
    @Override
    public Category addCategory(Category category) {
        return Optional.of(category).filter(newCategory -> !categoryRepository.existsByName(newCategory.getName()))
                .map(categoryRepository::save)
                .orElseThrow(() -> new CategoryNotFoundException("Category already exists!"));
    }

    @Transactional
    @Override
    public void deleteCategory(Long id) {
        repository.findById(id).ifPresentOrElse(repository::delete, () -> {
            throw new CategoryNotFoundException("Category not found");
        });
    }

    @Transactional
    @Override
    public Category updateCategory(Category category, Long id) {
        return Optional.ofNullable(getCategoryById(id)).map(oldCategory -> {
            oldCategory.setName(category.getName());
            return repository.save(oldCategory);
        }).orElseThrow(() -> new CategoryNotFoundException("Category not found!"));
    }

    @Override
    public List<Category> getAllCategories() {
        return repository.findAll();
    }

    @Override
    public Category getCategoryById(Long id) {
        return repository.findById(id).orElseThrow(()-> new CategoryNotFoundException("Category not found!"));
    }

    @Override
    public Category getCategoryByName(String name) {
        return repository.findByName(name);
    }
}
