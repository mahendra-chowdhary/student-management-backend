
package com.coder.fe_be.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;
import org.springframework.beans.factory.annotation.Value;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.servers.Server;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class OpenApiConfig {

    @Value("${app.swagger.server-url:http://localhost:8080}")
    private String serverUrl;

    @Bean
    public OpenAPI customOpenAPI() {

        return new OpenAPI()
                .info(new Info()
                        .title("Student Management System API")
                        .description("REST APIs for managing student records.")
                        .version("1.0.0"))
                .addServersItem(new Server()
                        .url(serverUrl));
    }
}

