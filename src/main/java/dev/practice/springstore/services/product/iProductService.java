package dev.practice.springstore.services.product;

import dev.practice.springstore.models.Product;
import dev.practice.springstore.requests.AddProduct;

import java.util.List;

public interface iProductService {
    Product addProduct(AddProduct request);

    List<Product> getAllProducts();

    Product getProductById(Long id);

    void deleteProduct(Long id);

    void updateProduct(Product product, Long id);

    List<Product> getProductsByCategory (String category);

    List<Product> getProductsByBrand (String brand);

    List<Product> getProductsByName (String name);

    List<Product> getProductsByCategoryAndBrand(String category, String brand);

    List<Product> getProductsByBrandAndName(String brand, String name);

    List<Product> getProductsByCategoryAndName(String category, String name);

    Long countProductsByBrandAndName(String brand, String name);

}
