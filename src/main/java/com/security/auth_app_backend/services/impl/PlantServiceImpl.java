package com.security.auth_app_backend.services.impl;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.security.auth_app_backend.dtos.PlantRequest;
import com.security.auth_app_backend.dtos.PlantResponse;
import com.security.auth_app_backend.entities.Plant;
import com.security.auth_app_backend.repositories.PlantRepository;
import com.security.auth_app_backend.services.PlantService;

@Service
public class PlantServiceImpl implements PlantService {

    private final PlantRepository plantRepository;

    public PlantServiceImpl(PlantRepository plantRepository) {
        this.plantRepository = plantRepository;
    }

    @Override
    public List<PlantResponse> getAllPlants() {
        return plantRepository.findAll()
                .stream()
                .map(PlantResponse::fromEntity)
                .toList();
    }

    @Override
    public PlantResponse getPlantById(Long id) {
        return plantRepository.findById(id)
                .map(PlantResponse::fromEntity)
                .orElse(null);
    }

    @Override
    public PlantResponse addPlant(PlantRequest plantRequest) {
        Plant plant = new Plant();
        plant.setName(plantRequest.getName());
        plant.setCategory(plantRequest.getCategory());
        plant.setTag(plantRequest.getTag());
        plant.setPrice(plantRequest.getPrice());
        plant.setImageUrl(plantRequest.getImageUrl());
        plant.setQuantity(plantRequest.getQuantity());
        Plant savedPlant = plantRepository.save(plant); 
        return PlantResponse.fromEntity(savedPlant);
    }

    @Override
    public PlantResponse update(Long id, PlantRequest request, MultipartFile image) {

        Plant plant = plantRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Plant not found"));

        plant.setName(request.getName());
        plant.setCategory(request.getCategory());
        plant.setTag(request.getTag());
        plant.setPrice(request.getPrice());
        plant.setQuantity(request.getQuantity());

        if (image != null && !image.isEmpty()) {
            String imagePath = saveImage(image);
            plant.setImageUrl(imagePath);
        }

        return PlantResponse.fromEntity(
                plantRepository.save(plant)
        );
    }


    private String saveImage(MultipartFile image) {
          try {
        if (image == null || image.isEmpty()) {
            return null;
        }

        // 📂 Upload directory
        String uploadDir = "uploads";
        Path uploadPath = Paths.get(uploadDir);

        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        // 🆔 Unique filename
        String originalName = image.getOriginalFilename();
        String fileName = UUID.randomUUID() + "_" + originalName;

        // 💾 Save file
        Path filePath = uploadPath.resolve(fileName);
        Files.copy(image.getInputStream(), filePath);

        // 🔗 Path saved in DB
        return uploadDir + "/" + fileName;

    } catch (IOException e) {
        throw new RuntimeException("Failed to store image", e);
    }
    }

    @Override
    public boolean deletePlant(Long id) {
        if (!plantRepository.existsById(id)) {
            return false;
        }
        plantRepository.deleteById(id);
        return true;
    }
}
