package com.example.springbootbackend.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.tags.Tag;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * OpenAPI configuration for the Quality Defect Management System (QDMS) backend.
 */
@Configuration
public class OpenApiConfig {

    /**
     * PUBLIC_INTERFACE
     * Provides OpenAPI metadata (title/description/version) and stable tag ordering.
     */
    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Quality Defect Management System API")
                        .description("Spring Boot REST API for defect logging, RCA (5 Whys), corrective actions, dashboards, and reporting.")
                        .version("0.1.0"))
                .tags(List.of(
                        new Tag().name("Auth").description("Authentication endpoints (JWT)"),
                        new Tag().name("Users").description("User and role management"),
                        new Tag().name("Defects").description("Defect CRUD and lifecycle"),
                        new Tag().name("RCA").description("Root cause analysis (5 Whys)"),
                        new Tag().name("Actions").description("Corrective actions tracking"),
                        new Tag().name("Dashboard").description("Aggregated dashboard metrics"),
                        new Tag().name("Reports").description("Reporting and PDF export")
                ));
    }
}
