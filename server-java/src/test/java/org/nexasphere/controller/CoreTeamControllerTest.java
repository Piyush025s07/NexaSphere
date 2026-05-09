package org.nexasphere.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.nexasphere.model.entity.CoreTeamMemberEntity;
import org.nexasphere.repository.CoreTeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class CoreTeamControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CoreTeamRepository coreTeamRepository;

    @BeforeEach
    void setUp() {
        coreTeamRepository.deleteAll();
    }

    @Test
    void publicEndpoint_accessibleWithoutAuth() throws Exception {
        mockMvc.perform(get("/api/content/core-team"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    void adminEndpoint_withoutToken_returnsUnauthorized() throws Exception {
        mockMvc.perform(get("/api/admin/core-team"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void addMember_withValidData_returnsCreated() throws Exception {
        String token = loginAndGetToken();

        mockMvc.perform(post("/api/admin/core-team")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(validMemberJson("rahul.sharma@example.com", "9876543210")))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.name").value("Rahul Sharma"))
                .andExpect(jsonPath("$.section").value("A"));
    }

    @Test
    void addMember_invalidEmail_returnsBadRequest() throws Exception {
        String token = loginAndGetToken();

        mockMvc.perform(post("/api/admin/core-team")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(validMemberJson("invalid-email", "9876543210")))
                .andExpect(status().isBadRequest());
    }

    @Test
    void addMember_invalidWhatsapp_returnsBadRequest() throws Exception {
        String token = loginAndGetToken();

        mockMvc.perform(post("/api/admin/core-team")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(validMemberJson("rahul.sharma@example.com", "12345")))
                .andExpect(status().isBadRequest());
    }

    @Test
    void addMember_invalidLinkedinUrl_returnsBadRequest() throws Exception {
        String token = loginAndGetToken();

        mockMvc.perform(post("/api/admin/core-team")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(validMemberJson("rahul.sharma@example.com", "9876543210")
                                .replace("https://linkedin.com/in/rahulsharma", "linkedin.com/in/rahulsharma")))
                .andExpect(status().isBadRequest());
    }

    @Test
    void addMember_invalidSection_returnsBadRequest() throws Exception {
        String token = loginAndGetToken();

        mockMvc.perform(post("/api/admin/core-team")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(validMemberJson("rahul.sharma@example.com", "9876543210")
                                .replace("\"section\": \"A\"", "\"section\": \"AB\"")))
                .andExpect(status().isBadRequest());
    }

    @Test
    void deleteExistingMember_returnsOkTrue() throws Exception {
        String token = loginAndGetToken();
        CoreTeamMemberEntity saved = coreTeamRepository.save(sampleMember("delete.me@example.com", "9876543210"));

        mockMvc.perform(delete("/api/admin/core-team/{id}", saved.getId())
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.ok").value(true));
    }

    @Test
    void deleteMissingMember_returnsNotFound() throws Exception {
        String token = loginAndGetToken();

        mockMvc.perform(delete("/api/admin/core-team/{id}", 999999)
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isNotFound());
    }

    private String loginAndGetToken() throws Exception {
        String loginJson = "{\"email\": \"nexasphere@glbajajgroup.org\", \"password\": \"Admin@123\"}";
        String response = mockMvc.perform(post("/api/admin/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(loginJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").exists())
                .andReturn()
                .getResponse()
                .getContentAsString();
        return response.split("\"token\":\"")[1].split("\"")[0];
    }

    private String validMemberJson(String email, String whatsapp) {
        return """
                {
                  "name": "Rahul Sharma",
                  "role": "Core Team Member",
                  "year": "2nd Year",
                  "branch": "CSE (AI & ML)",
                  "section": "A",
                  "email": "%s",
                  "whatsapp": "%s",
                  "linkedin": "https://linkedin.com/in/rahulsharma",
                  "instagram": "https://instagram.com/rahulsharma",
                  "photoUrl": "https://example.com/photos/rahul.jpg"
                }
                """.formatted(email, whatsapp);
    }

    private CoreTeamMemberEntity sampleMember(String email, String whatsapp) {
        CoreTeamMemberEntity member = new CoreTeamMemberEntity();
        member.setName("Delete Me");
        member.setRole("Core Team Member");
        member.setYear("2nd Year");
        member.setBranch("CSE");
        member.setSection("A");
        member.setEmail(email);
        member.setWhatsapp(whatsapp);
        member.setLinkedin("https://linkedin.com/in/deleteme");
        member.setInstagram("https://instagram.com/deleteme");
        member.setPhotoUrl("https://example.com/photos/deleteme.jpg");
        return member;
    }
}
