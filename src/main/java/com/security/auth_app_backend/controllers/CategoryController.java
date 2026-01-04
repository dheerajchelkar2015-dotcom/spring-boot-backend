package com.security.auth_app_backend.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.security.auth_app_backend.dtos.CategoryDTO;
import com.security.auth_app_backend.services.impl.CategoryServiceImpl;

@RestController
@RequestMapping("/api/v1/categories")
public class CategoryController {
    private final CategoryServiceImpl categoryServiceImpl;

    public CategoryController(CategoryServiceImpl categoryServiceImpl){
        this.categoryServiceImpl = categoryServiceImpl;
    }

    @PostMapping
    public CategoryDTO create(@RequestBody CategoryDTO dto){
        return categoryServiceImpl.creaCategory(dto);
    }

    @GetMapping
    public List<CategoryDTO> getAll(){
        return categoryServiceImpl.getAllCategories();
    }
}
