package dev.practice.springstore.services.product;

import dev.practice.springstore.dto.ImageDTO;
import dev.practice.springstore.dto.ProductDTO;
import dev.practice.springstore.exceptions.ProductNotFoundException;
import dev.practice.springstore.models.product.Category;
import dev.practice.springstore.models.product.Image;
import dev.practice.springstore.models.product.Product;
import dev.practice.springstore.repository.CategoryRepository;
import dev.practice.springstore.repository.ImageRepository;
import dev.practice.springstore.repository.ProductRepository;
import dev.practice.springstore.requests.AddProductRequest;
import dev.practice.springstore.requests.ProductUpdateRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.modelmapper.ModelMapper;


import java.util.*;

@Service
@RequiredArgsConstructor
public class ProductServices implements iProductService {
    final private ProductRepository productRepository;

    final private CategoryRepository categoryRepository;
    private final ImageRepository imageRepository;

    private final ModelMapper modelMapper = new ModelMapper();

    @Transactional
    @Override
    public Product addProduct(AddProductRequest request) {
        Set<Integer> set = new HashSet<>();
        Category category = Optional.ofNullable(categoryRepository.findByName(request.getCategory().getName()))
                .orElseGet(() -> {
                    Category newCategory = new Category(request.getCategory().getName());
                    return categoryRepository.save(newCategory);
                });
        request.setCategory(category);
        Product newProduct = new Product(
                request.getName(),
                request.getPrice(),
                request.getInventory(),
                category,
                request.getDescription(),
                request.getBrand()
        );
        return productRepository.save(newProduct);
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product getProductById(Long id) {
        return productRepository.findById(id).orElseThrow(ProductNotFoundException::new);
    }

    @Transactional
    @Override
    public void deleteProduct(Long id) {
        productRepository.findById(id).ifPresentOrElse(productRepository::delete, ProductNotFoundException::new);
    }

    @Transactional
    @Override
    public Product updateProduct(ProductUpdateRequest request, Long id) {
        return productRepository.findById(id)
                .map(existingProduct -> {
                    existingProduct.setBrand(request.getBrand());
                    existingProduct.setName(request.getName());
                    existingProduct.setPrice(request.getPrice());
                    existingProduct.setDescription(request.getDescription());
                    existingProduct.setInventory(request.getInventory());
                    existingProduct.setCategory(categoryRepository.findByName(request.getCategory().getName()));
                    return productRepository.save(existingProduct);
                })
                .orElseThrow(ProductNotFoundException::new);
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

    @Override
    public ProductDTO convertToDTO(Product createdProduct) {
        ProductDTO productDTO = modelMapper.map(createdProduct, ProductDTO.class);
        List<Image> images = imageRepository.findByProductId(productDTO.getId());
        List<ImageDTO> imageDTOS = images.stream()
                .map(image -> modelMapper.map(image, ImageDTO.class))
                .toList();
        productDTO.setImages(imageDTOS);
        return productDTO;
    }
}
