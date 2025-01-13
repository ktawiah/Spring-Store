package dev.practice.springstore.controller;

import dev.practice.springstore.models.product.Category;
import dev.practice.springstore.response.ApiResponse;
import dev.practice.springstore.services.category.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/categories")
public class CategoryController {
    final private CategoryService categoryService;

    @GetMapping("/category/{name}/category")
    private ResponseEntity<ApiResponse> getCategoryByName(String name) {
        try {
            Category retrievedCategory = categoryService.getCategoryByName(name);
            return ResponseEntity.ok(new ApiResponse("Found", retrievedCategory));
        } catch (Exception e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @PostMapping("/create")
    private ResponseEntity<ApiResponse> addCategory(@RequestBody Category category) {
        try {
            Category savedCategory = categoryService.addCategory(category);
            URI location = UriComponentsBuilder.fromPath("{id}").buildAndExpand(savedCategory.getId()).toUri();
            return ResponseEntity.created(location).body(new ApiResponse("", savedCategory));
        } catch (Exception e) {
            return ResponseEntity.status(CONFLICT).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping
    private ResponseEntity<ApiResponse> getAllCategories() {
        try {
            List<Category> categories = categoryService.getAllCategories();
            return ResponseEntity.ok(new ApiResponse("Retrieval Success!", categories));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse("Oops. Something went wrong!", INTERNAL_SERVER_ERROR));
        }
    }

    @DeleteMapping("/category/{categoryId}/delete")
    private ResponseEntity<ApiResponse> deleteCategory(@PathVariable Long categoryId) {
        try {
            Category category = categoryService.getCategoryById(categoryId);

            if (category != null) {
                categoryService.deleteCategory(categoryId);
                return ResponseEntity.noContent().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
        return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse("Delete failed!", null));
    }

    @PutMapping("/category/{categoryId}/update")
    private ResponseEntity<ApiResponse> updateCategory(@PathVariable Long categoryId, @RequestBody Category category) {
        try {
            Category updatedCategory = categoryService.updateCategory(category, categoryId);
            return ResponseEntity.ok(new ApiResponse("Category update success!", updatedCategory));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }
}
