package com.security.auth_app_backend.config;

public class AppConstants {
    public static final String[] AUTH_PUBLIC_URLS = {
        "/v3/api-docs/**",
        "/swagger-ui.html",
        "/swagger-ui/**",
        "/api/v1/auth/**",
        "/uploads/**",
    };

    public static final String[] AUTH_ADMIN_URLS = {
        "/api/v1/users/**"
    };

    public static final String[] AUTH_GUEST_URLS = {
        
    };

    public static final String ADMIN_ROLE="ADMIN";
    public static final String GUEST_ROLE="GUEST";
}
