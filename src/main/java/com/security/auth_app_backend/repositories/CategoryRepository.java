package com.security.auth_app_backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.security.auth_app_backend.entities.Category;

public interface CategoryRepository extends JpaRepository<Category,Long>{
    
}
