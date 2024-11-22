package dev.practice.springstore.services.product;

import dev.practice.springstore.models.Product;
import dev.practice.springstore.requests.AddProductRequest;
import dev.practice.springstore.requests.ProductUpdateRequest;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface iProductService {
    Product addProduct(AddProductRequest request);

    List<Product> getAllProducts();

    Product getProductById(Long id);

    void deleteProduct(Long id);

    Product updateProduct(ProductUpdateRequest request, Long id);

    List<Product> getProductsByCategory (String category);

    List<Product> getProductsByBrand (String brand);

    List<Product> getProductsByName (String name);

    List<Product> getProductsByCategoryAndBrand(String category, String brand);

    List<Product> getProductsByBrandAndName(String brand, String name);

    List<Product> getProductsByCategoryAndName(String category, String name);

    Long countProductsByBrandAndName(String brand, String name);

}
