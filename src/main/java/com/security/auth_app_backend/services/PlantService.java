package com.security.auth_app_backend.services;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.security.auth_app_backend.dtos.PlantRequest;
import com.security.auth_app_backend.dtos.PlantResponse;

public interface PlantService {
    List<PlantResponse> getAllPlants();
    PlantResponse getPlantById(Long id);
    PlantResponse addPlant(PlantRequest plantRequest);
    PlantResponse update(
            Long id,
            PlantRequest request,
            MultipartFile image
    );
    boolean deletePlant(Long id);
}
