package com.security.auth_app_backend.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlantRequest {

    private String name;
    private String category;
    private String tag;
    private Integer price;
    private String imageUrl;
    private Integer quantity;

}

