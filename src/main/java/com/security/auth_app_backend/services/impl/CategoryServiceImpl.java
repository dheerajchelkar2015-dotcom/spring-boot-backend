package com.security.auth_app_backend.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.security.auth_app_backend.dtos.CategoryDTO;
import com.security.auth_app_backend.entities.Category;
import com.security.auth_app_backend.repositories.CategoryRepository;
import com.security.auth_app_backend.services.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService{

    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl (CategoryRepository categoryRepository){
        this.categoryRepository=categoryRepository;
    }

    @Override
    public CategoryDTO creaCategory(CategoryDTO categoryDTO) {
        Category category = new Category();
        category.setName(categoryDTO.getName());

        category = categoryRepository.save(category);
        categoryDTO.setId(categoryDTO.getId());
        return categoryDTO;

    }

    @Override
    public List<CategoryDTO> getAllCategories() {
        return categoryRepository.findAll().stream().map(cat -> {
            CategoryDTO dto = new CategoryDTO();
            dto.setId(cat.getId());
            dto.setName(cat.getName());
            return dto;
        }).collect(Collectors.toList());
    }


}
