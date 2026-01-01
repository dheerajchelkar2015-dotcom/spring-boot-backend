package com.security.auth_app_backend.controllers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.security.auth_app_backend.dtos.PlantRequest;
import com.security.auth_app_backend.dtos.PlantResponse;
import com.security.auth_app_backend.services.impl.PlantServiceImpl;

@RestController
@RequestMapping("/api/v1/plants")
public class PlantController {

    private final PlantServiceImpl plantService;

    @Value("${file.upload-dir}")
    private String uploadDir;

    public PlantController(PlantServiceImpl plantService) {
        this.plantService = plantService;
    }

    @GetMapping
    public List<PlantResponse> getAllPlants() {
        return plantService.getAllPlants();
    }

    @GetMapping("/{id}")
    public PlantResponse getPlantById(@PathVariable Long id) {
        return plantService.getPlantById(id);
    }

    // ✅ Create plant with image upload
    @PostMapping
    public PlantResponse createPlant(
            @RequestPart("plant") PlantRequest plantRequest,
            @RequestPart("image") MultipartFile file
    ) throws IOException {

        // ✅ Absolute upload path
        Path uploadPath = Paths.get(uploadDir).toAbsolutePath().normalize();
        Files.createDirectories(uploadPath);

        // ✅ Safe unique filename
        String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
        Path filePath = uploadPath.resolve(fileName);

        Files.copy(file.getInputStream(), filePath);

        // ✅ Store relative path only
        plantRequest.setImageUrl("uploads/" + fileName);

        return plantService.addPlant(plantRequest);
    }

@PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
public PlantResponse updatePlant(
        @PathVariable Long id,
        @RequestPart("plant") PlantRequest request,
        @RequestPart(value = "image", required = false) MultipartFile image
) {
    return plantService.update(id, request, image);
}


    @DeleteMapping("/{id}")
    public boolean deletePlant(@PathVariable Long id) {
        return plantService.deletePlant(id);
    }
}
