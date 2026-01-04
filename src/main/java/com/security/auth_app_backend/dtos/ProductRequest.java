package com.security.auth_app_backend.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductRequest {

    private String name;

    private String tag;
    private Integer price;

    private Integer quantity;

    private String imageUrl;
    private Long categoryId;
}
