package com.example.springbootbackend;

import com.example.springbootbackend.api.dto.AuthDtos;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Basic integration tests to validate JWT auth works and protected endpoints reject unauthenticated access.
 */
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(properties = {
        // Use H2 for tests to avoid requiring a MySQL instance in CI.
        "spring.datasource.url=jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1",
        "spring.datasource.driverClassName=org.h2.Driver",
        "spring.datasource.username=sa",
        "spring.datasource.password=",
        "spring.jpa.hibernate.ddl-auto=create-drop",
        "qdms.jwt.secret=test-secret-test-secret-test-secret-32chars"
})
public class AuthAndSecurityTests {

    @Autowired
    MockMvc mvc;

    @Autowired
    ObjectMapper om;

    @Test
    void unauthenticated_dashboard_is_401() throws Exception {
        mvc.perform(get("/api/dashboard/overdue-actions"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void login_then_access_dashboard_is_200() throws Exception {
        String body = om.writeValueAsString(new AuthDtos.LoginRequest("admin", "admin123"));

        String token = mvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accessToken").exists())
                .andReturn()
                .getResponse()
                .getContentAsString();

        String accessToken = om.readTree(token).get("accessToken").asText();

        mvc.perform(get("/api/dashboard/overdue-actions")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.overdueCount").exists());
    }
}
