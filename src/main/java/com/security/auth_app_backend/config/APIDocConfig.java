package com.security.auth_app_backend.config;

import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;

@Configuration
@OpenAPIDefinition(
    info =  @Info(
        title = "Auth Application",
        description = "Genric auth application",
        contact = @Contact(
            name = "Dheeraj",
            url = ""
        ),
        version = "1.0",
        summary = "This app is very usefull if you don't want create auth app from scratch"
    ),
    security = {
        @SecurityRequirement(
            name = "bearerAuth"
        )
    }
)
@SecurityScheme(
    name = "bearerAuth",
    type = SecuritySchemeType.HTTP,
    scheme = "bearer",
    bearerFormat = "JWT"
)
public class APIDocConfig {

}
