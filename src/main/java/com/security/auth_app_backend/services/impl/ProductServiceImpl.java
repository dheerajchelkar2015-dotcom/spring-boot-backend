package com.security.auth_app_backend.services.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.security.auth_app_backend.dtos.ProductRequest;
import com.security.auth_app_backend.dtos.ProductResponse;
import com.security.auth_app_backend.entities.Category;
import com.security.auth_app_backend.entities.Product;
import com.security.auth_app_backend.repositories.CategoryRepository;
import com.security.auth_app_backend.repositories.ProductRepository;
import com.security.auth_app_backend.services.ProductService;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public ProductServiceImpl(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<ProductResponse> getAllProducts() {
        return productRepository.findAll()
                .stream()
                .map(ProductResponse::fromEntity)
                .toList();
    }

    @Override
    public ProductResponse getProductById(Long id) {
        return productRepository.findById(id)
                .map(ProductResponse::fromEntity)
                .orElse(null);
    }

    @Override
    public ProductResponse addProduct(ProductRequest productRequest) {

        Product product = new Product();
        product.setName(productRequest.getName());
        product.setTag(productRequest.getTag());
        product.setPrice(productRequest.getPrice());
        product.setQuantity(productRequest.getQuantity());
        product.setImageUrl(productRequest.getImageUrl());

        // 🔹 Fetch Category
        Category category = categoryRepository.findById(productRequest.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));

        product.setCategory(category);

        Product savedProduct = productRepository.save(product);
        return ProductResponse.fromEntity(savedProduct);
    }

    @Override
    public ProductResponse update(Long id, ProductRequest request) {

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        product.setName(request.getName());
        product.setTag(request.getTag());
        product.setPrice(request.getPrice());
        product.setQuantity(request.getQuantity());

        if (request.getImageUrl() != null) {
            product.setImageUrl(request.getImageUrl());
        }

        // 🔹 Update category if provided
        if (request.getCategoryId() != null) {
            Category category = categoryRepository.findById(request.getCategoryId())
                    .orElseThrow(() -> new RuntimeException("Category not found"));
            product.setCategory(category);
        }

        return ProductResponse.fromEntity(productRepository.save(product));
    }

    @Override
    public boolean deleteProduct(Long id) {
        if (!productRepository.existsById(id)) {
            return false;
        }
        productRepository.deleteById(id);
        return true;
    }
}
