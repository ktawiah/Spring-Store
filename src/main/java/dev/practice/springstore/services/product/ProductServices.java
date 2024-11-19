package dev.practice.springstore.services.product;

import dev.practice.springstore.exceptions.ProductNotFoundException;
import dev.practice.springstore.models.Category;
import dev.practice.springstore.models.Product;
import dev.practice.springstore.repository.CategoryRepository;
import dev.practice.springstore.repository.ProductRepository;
import dev.practice.springstore.requests.AddProduct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductServices implements iProductService {
    final private ProductRepository productRepository;
    final private CategoryRepository categoryRepository;

    @Override
    public Product addProduct(AddProduct request) {
        Category category = Optional.ofNullable(categoryRepository.findByName(request.getCategory().getName()))
                .orElseGet(() -> {
                    Category newCategory = new Category(request.getCategory().getName());
                    return categoryRepository.save(newCategory);
                });
        request.setCategory(category);
        return productRepository.save(createProduct(request, category));
    }

    private Product createProduct(AddProduct product, Category category) {
        return new Product(
                product.getName(),
                product.getPrice(),
                product.getBrand(),
                product.getDescription(),
                product.getInventory(),
                category
        );
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product getProductById(Long id) {
        return productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException("Product not Found!"));
    }

    @Override
    public void deleteProduct(Long id) {
        productRepository.findById(id).ifPresentOrElse(productRepository::delete, () -> {
            throw new ProductNotFoundException("Product not Found!");
        });
    }

    @Override
    public void updateProduct(Product product, Long id) {

    }

    @Override
    public List<Product> getProductsByCategory(String category) {
        return productRepository.findByCategoryName(category);
    }

    @Override
    public List<Product> getProductsByBrand(String brand) {
        return productRepository.findByBrand(brand);
    }

    @Override
    public List<Product> getProductsByName(String name) {
        return productRepository.findByName(name);
    }

    @Override
    public List<Product> getProductsByCategoryAndBrand(String category, String brand) {
        return productRepository.findByCategoryNameAndBrand(category, brand);
    }

    @Override
    public List<Product> getProductsByBrandAndName(String brand, String name) {
        return productRepository.findByBrandAndName(brand, name);
    }

    @Override
    public List<Product> getProductsByCategoryAndName(String category, String name) {
        return productRepository.findByCategoryNameAndName(category, name);
    }

    @Override
    public Long countProductsByBrandAndName(String brand, String name) {
        return productRepository.countByBrandAndName(brand, name);
    }
}
