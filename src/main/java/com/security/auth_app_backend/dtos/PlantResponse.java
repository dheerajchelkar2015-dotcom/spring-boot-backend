package com.security.auth_app_backend.dtos;

public record PlantResponse(
        Long id,
        String name,
        String category,
        String tag,
        Integer price,
        String imageUrl,
        Integer quantity

) {
    public static PlantResponse fromEntity(
            com.security.auth_app_backend.entities.Plant plant
    ) {
        return new PlantResponse(
                plant.getId(),
                plant.getName(),
                plant.getCategory(),
                plant.getTag(),
                plant.getPrice(),
                plant.getImageUrl(),
                plant.getQuantity()
        );
    }
}
