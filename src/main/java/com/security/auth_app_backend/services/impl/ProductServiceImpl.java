package com.security.auth_app_backend.services.impl;


import java.util.List;

import org.springframework.stereotype.Service;

import com.security.auth_app_backend.dtos.ProductRequest;
import com.security.auth_app_backend.dtos.ProductResponse;
import com.security.auth_app_backend.entities.Product;
import com.security.auth_app_backend.repositories.ProductRepository;
import com.security.auth_app_backend.services.ProductService;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
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
        product.setCategory(productRequest.getCategory());
        product.setTag(productRequest.getTag());
        product.setPrice(productRequest.getPrice());
        product.setImageUrl(productRequest.getImageUrl());
        product.setQuantity(productRequest.getQuantity());
        Product savedProduct = productRepository.save(product);
        return ProductResponse.fromEntity(savedProduct);
    }

    @Override
    public ProductResponse update(Long id, ProductRequest request) {

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        product.setName(request.getName());
        product.setCategory(request.getCategory());
        product.setTag(request.getTag());
        product.setPrice(request.getPrice());
        product.setQuantity(request.getQuantity());

        if (request.getImageUrl() != null) {
            product.setImageUrl(request.getImageUrl());
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
