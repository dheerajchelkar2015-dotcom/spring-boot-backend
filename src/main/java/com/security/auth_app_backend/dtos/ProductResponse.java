package com.security.auth_app_backend.dtos;

import com.security.auth_app_backend.entities.Product;

public record ProductResponse(
        Long id,
        String name,
        CategoryDTO category,
        String tag,
        Integer price,
        String imageUrl,
        Integer quantity
) {
   public static ProductResponse fromEntity(Product product) {
    return new ProductResponse(
            product.getId(),                         // Long
            product.getName(),                       // String
            product.getCategory() != null            // CategoryDTO
                    ? new CategoryDTO(
                            product.getCategory().getId(),
                            product.getCategory().getName()
                      )
                    : null,
            product.getTag(),                         // String
            product.getPrice(),                       // Integer
            product.getImageUrl(),                    // String
            product.getQuantity()                     // Integer
    );
}

}
