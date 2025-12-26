package com.security.auth_app_backend.dtos;

public record LoginRequest(
    String email,
    String password
) {

}
