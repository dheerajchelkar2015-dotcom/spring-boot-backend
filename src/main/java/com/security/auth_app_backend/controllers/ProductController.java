package com.security.auth_app_backend.controllers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.security.auth_app_backend.dtos.ProductRequest;
import com.security.auth_app_backend.dtos.ProductResponse;
import com.security.auth_app_backend.services.impl.ProductServiceImpl;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

    private final ProductServiceImpl productService;

    @Value("${file.upload-dir}")
    private String uploadDir;

    private String saveImage(MultipartFile file) throws IOException {
        Path uploadPath = Paths.get(uploadDir).toAbsolutePath().normalize();
        Files.createDirectories(uploadPath);

        String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
        Path filePath = uploadPath.resolve(fileName);

        Files.copy(file.getInputStream(), filePath);

        return "uploads/" + fileName;
    }

    public ProductController(ProductServiceImpl productService) {
        this.productService = productService;
    }

    @GetMapping
    public List<ProductResponse> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/{id}")
    public ProductResponse getProductById(@PathVariable Long id) {
        return productService.getProductById(id);
    }

    @PostMapping
    public ProductResponse createProduct(
            @RequestPart("product") ProductRequest productRequest,
            @RequestPart("image") MultipartFile file) throws IOException {

        String imagePath = saveImage(file);
        productRequest.setImageUrl(imagePath);

        return productService.addProduct(productRequest);
    }

    @PutMapping("/{id}")
    public ProductResponse updateProduct(
            @PathVariable Long id,
            @RequestPart("product") ProductRequest request,
            @RequestPart(value = "image", required = false) MultipartFile image) throws IOException {

        if (image != null && !image.isEmpty()) {
            request.setImageUrl(saveImage(image));
        }

        return productService.update(id, request);
    }

    @DeleteMapping("/{id}")
    public boolean deleteProduct(@PathVariable Long id) {
        return productService.deleteProduct(id);
    }
}
