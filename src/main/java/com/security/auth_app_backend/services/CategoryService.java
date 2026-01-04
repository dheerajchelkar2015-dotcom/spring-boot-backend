package com.security.auth_app_backend.services;

import java.util.List;

import com.security.auth_app_backend.dtos.CategoryDTO;

public interface CategoryService {
    CategoryDTO creaCategory(CategoryDTO categoryDTO);

    List<CategoryDTO> getAllCategories();
}
