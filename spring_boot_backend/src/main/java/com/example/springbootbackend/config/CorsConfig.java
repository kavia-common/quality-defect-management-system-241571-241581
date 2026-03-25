package com.example.springbootbackend.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * CORS configuration for browser-based frontend previews.
 *
 * Reads configuration from environment variables (wired through application/container env):
 * - ALLOWED_ORIGINS: comma-separated list of allowed origins (e.g., https://...:3000,http://localhost:3000)
 * - ALLOWED_HEADERS: comma-separated list of headers (must include Authorization)
 * - ALLOWED_METHODS: comma-separated list of HTTP methods
 * - CORS_MAX_AGE: seconds
 */
@Configuration
public class CorsConfig {

    private static List<String> splitCsv(String csv) {
        if (csv == null || csv.trim().isEmpty()) return List.of();
        return Arrays.stream(csv.split(","))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .collect(Collectors.toList());
    }

    /**
     * PUBLIC_INTERFACE
     * Spring Security will call this bean when http.cors(Customizer.withDefaults()) is enabled.
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource(
            @Value("${ALLOWED_ORIGINS:}") String allowedOrigins,
            @Value("${ALLOWED_HEADERS:Content-Type,Authorization}") String allowedHeaders,
            @Value("${ALLOWED_METHODS:GET,POST,PUT,DELETE,PATCH,OPTIONS}") String allowedMethods,
            @Value("${CORS_MAX_AGE:3600}") long maxAgeSeconds
    ) {
        CorsConfiguration cfg = new CorsConfiguration();

        // Important: do not use "*" with allowCredentials=true in modern browsers.
        List<String> origins = splitCsv(allowedOrigins);
        if (origins.isEmpty()) {
            // Safe defaults for local dev; previews should provide ALLOWED_ORIGINS explicitly.
            cfg.setAllowedOrigins(List.of("http://localhost:3000"));
        } else {
            cfg.setAllowedOrigins(origins);
        }

        cfg.setAllowedHeaders(splitCsv(allowedHeaders));
        cfg.setAllowedMethods(splitCsv(allowedMethods));
        cfg.setAllowCredentials(true);
        cfg.setMaxAge(maxAgeSeconds);

        // Expose Authorization if you later decide to send refreshed tokens in headers.
        cfg.setExposedHeaders(List.of("Authorization"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", cfg);
        return source;
    }
}
