package com.coder.fe_be.config;

import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@Configuration
@OpenAPIDefinition(
    info = @Info(
        title = "Student Management System API",
        description = "REST APIs for managing student records, including adding, retrieving, updating, and deleting students.",
        version = "1.0.0"
    )
)
public class OpenApiConfig {

}

