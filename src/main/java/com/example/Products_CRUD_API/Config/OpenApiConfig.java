package com.example.Products_CRUD_API.Config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {

        SecurityScheme jwtScheme = new SecurityScheme()
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT");

        return new OpenAPI()
                .info(new Info()
                        .title("Products CRUD API")
                        .description("REST API for managing products and items with JWT security")
                        .version("v1"))
                .components(
                        new Components().addSecuritySchemes("JWT", jwtScheme)
                )
                .addSecurityItem(
                        new SecurityRequirement().addList("JWT")
                );
    }
}