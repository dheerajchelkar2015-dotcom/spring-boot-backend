package com.security.auth_app_backend.dtos;

import com.security.auth_app_backend.entities.Product;

public record ProductResponse(
        Long id,
        String name,
        String category,
        String tag,
        Integer price,
        String imageUrl,
        Integer quantity

) {
    public static ProductResponse fromEntity(
            Product product
    ) {
        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getCategory(),
                product.getTag(),
                product.getPrice(),
                product.getImageUrl(),
                product.getQuantity()
        );
    }
}