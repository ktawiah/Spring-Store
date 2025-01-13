package dev.practice.springstore.controller;

import dev.practice.springstore.dto.ProductDTO;
import dev.practice.springstore.models.product.Product;
import dev.practice.springstore.requests.AddProductRequest;
import dev.practice.springstore.requests.ProductUpdateRequest;
import dev.practice.springstore.response.ApiResponse;
import dev.practice.springstore.services.product.ProductServices;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@RestController
@RequestMapping(value = "${api.prefix}/products",
        produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
@RequiredArgsConstructor
public class ProductController {
    final private ProductServices productService;

    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    private ResponseEntity<ApiResponse> addProduct(@RequestBody AddProductRequest product) {
        try {
            Product createdProduct = productService.addProduct(product);
            ProductDTO productDto = productService.convertToDTO(createdProduct);
            return ResponseEntity.ok(new ApiResponse("Created!", productDto));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping
    private ResponseEntity<ApiResponse> getAllProducts() {
        try {
            List<Product> products = productService.getAllProducts();
            List<ProductDTO> productDTOS = products.stream().map(productService::convertToDTO).toList();
            return ResponseEntity.ok().body(new ApiResponse("Success!", productDTOS));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @PutMapping("/product/{id}/update")
    private ResponseEntity<ApiResponse> updateProduct(@RequestBody ProductUpdateRequest request, @PathVariable Long id) {
        try {
            Product updatedProduct = productService.updateProduct(request, id);
            return ResponseEntity.ok(new ApiResponse("Update success!", productService.convertToDTO(updatedProduct)));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/product/{id}/delete")
    private ResponseEntity<ApiResponse> dropProduct(@PathVariable Long id) {
        try {
            productService.deleteProduct(id);
            return ResponseEntity.ok(new ApiResponse("Delete success!", null));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/product/{id}")
    private ResponseEntity<ApiResponse> retrieveProductById(@PathVariable Long id) {
        try {
            Product product = productService.getProductById(id);
            return ResponseEntity.ok().body(new ApiResponse("Found", productService.convertToDTO(product)));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/product/by-name")
    private ResponseEntity<ApiResponse> retrieveProductByName(@RequestParam String name) {
        try {
            List<Product> products = productService.getProductsByName(name);
            List<ProductDTO> productDTOS = products.stream().map(productService::convertToDTO).toList();
            return ResponseEntity.ok().body(new ApiResponse("Found", productDTOS));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/product/by-category")
    private ResponseEntity<ApiResponse> retrieveProductsByCategoryName(@RequestParam(name = "category-name") String categoryName) {
        try {
            List<Product> productList = productService.getProductsByCategory(categoryName);
            List<ProductDTO> productDTOS = productList.stream().map(productService::convertToDTO).toList();
            return ResponseEntity.ok().body(new ApiResponse("Success!", productDTOS));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/product/by-brand")
    private ResponseEntity<ApiResponse> retrieveProductsByBrand(@RequestParam("brand") String brand) {
        try {
            List<Product> products = productService.getProductsByBrand(brand);
            List<ProductDTO> productDTOS = products.stream().map(productService::convertToDTO).toList();
            return ResponseEntity.ok(new ApiResponse("Success!", productDTOS));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/product/by-brand-and-category")
    private ResponseEntity<ApiResponse> retrieveProductsByBrandAndCategory(@RequestParam("brand") String brand, @RequestParam("category") String category) {
        try {
            List<Product> products = productService.getProductsByCategoryAndBrand(category, brand);
            List<ProductDTO> productDTOS = products.stream().map(productService::convertToDTO).toList();
            return ResponseEntity.ok(new ApiResponse("Success!", productDTOS));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/product/by-brand-and-name")
    private ResponseEntity<ApiResponse> retrieveProductsByBrandAndName(@RequestParam("brand") String brand, @RequestParam("name") String name) {
        try {
            List<Product> products = productService.getProductsByBrandAndName(brand, name);
            List<ProductDTO> productDTOS = products.stream().map(productService::convertToDTO).toList();
            return ResponseEntity.ok(new ApiResponse("Success!", productDTOS));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/count")
    private ResponseEntity<ApiResponse> countProductsByNameAndBrand(@RequestParam("name") String name, @RequestParam("brand") String brand) {
        try {
            return ResponseEntity.ok(new ApiResponse("Success", productService.countProductsByBrandAndName(brand, name)));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
        }
    }
}
