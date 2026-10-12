package com.example.eventregistration.api;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class RegistrationControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void createsAndRetrievesRegistration() throws Exception {
        String requestBody = """
                {"name":"Ada Lovelace","email":"ada@example.com","eventName":"Tech Summit","eventDate":"%s"}
                """.formatted(LocalDate.now().plusDays(1));

        MvcResult result = mockMvc.perform(post("/api/registrations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.email").value("ada@example.com"))
                .andReturn();

        JsonNode registration = objectMapper.readTree(result.getResponse().getContentAsString());
        mockMvc.perform(get("/api/registrations/" + registration.get("id").asText()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Ada Lovelace"));
    }

    @Test
    void rejectsPastEventDate() throws Exception {
        String requestBody = """
                {"name":"Ada Lovelace","email":"ada@example.com","eventName":"Tech Summit","eventDate":"%s"}
                """.formatted(LocalDate.now().minusDays(1));

        mockMvc.perform(post("/api/registrations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.fieldErrors.eventDate").value("must be a future date"));
    }

    @Test
    void rejectsInvalidEmailAddress() throws Exception {
        String requestBody = """
                {"name":"Ada Lovelace","email":"not-an-email","eventName":"Tech Summit","eventDate":"%s"}
                """.formatted(LocalDate.now().plusDays(1));

        mockMvc.perform(post("/api/registrations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.fieldErrors.email").value("must be a valid email address"));
    }

    @Test
    void returnsNotFoundForUnknownRegistration() throws Exception {
        mockMvc.perform(get("/api/registrations/00000000-0000-0000-0000-000000000000"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("Not Found"));
    }
}