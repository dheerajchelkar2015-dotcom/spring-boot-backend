package com.security.auth_app_backend.services;

import java.util.List;

import com.security.auth_app_backend.dtos.ProductRequest;
import com.security.auth_app_backend.dtos.ProductResponse;

public interface ProductService {
    List<ProductResponse> getAllProducts();
    ProductResponse getProductById(Long id);
    ProductResponse addProduct(ProductRequest productRequest);
    ProductResponse update(Long id, ProductRequest request);
    boolean deleteProduct(Long id);
}
