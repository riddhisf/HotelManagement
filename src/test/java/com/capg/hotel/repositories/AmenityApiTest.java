package com.capg.hotel.repositories;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
class AmenityApiTest {

    @Autowired
    private MockMvc mockMvc;

    private static final String BASE = "/api/amenities";

    // ==========================
    // CREATE (POST)
    // ==========================

    @Test
    void shouldCreateAmenity() throws Exception {
        mockMvc.perform(post(BASE)
                .contentType(APPLICATION_JSON)
                .content("""
                        {
                          "name": "Test Amenity",
                          "description": "Valid description"
                        }
                        """))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"));
    }

    @Test
    void shouldReturnBadRequestWhenInvalidAmenity() throws Exception {
        mockMvc.perform(post(BASE)
                .contentType(APPLICATION_JSON)
                .content("""
                        {
                          "name": "",
                          "description": "abc"
                        }
                        """))
                .andExpect(status().isBadRequest());
    }

    // ==========================
    // UPDATE (PATCH)
    // ==========================

    @Test
    void shouldUpdateAmenity() throws Exception {
        String location = mockMvc.perform(post(BASE)
                .contentType(APPLICATION_JSON)
                .content("""
                        {
                          "name": "Temp",
                          "description": "Valid description"
                        }
                        """))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getHeader("Location");

        assertNotNull(location);

        String id = location.substring(location.lastIndexOf("/") + 1);

        mockMvc.perform(patch(BASE + "/" + id)
                .contentType("application/merge-patch+json")
                .content("""
                        {
                          "name": "Updated Name"
                        }
                        """))
                .andExpect(status().isNoContent());
    }

    @Test
    void shouldReturnNotFoundWhenUpdatingInvalidId() throws Exception {
        mockMvc.perform(patch(BASE + "/999999")
                .contentType("application/merge-patch+json")
                .content("""
                        {
                          "name": "Updated"
                        }
                        """))
                .andExpect(status().isNotFound());
    }

    // ==========================
    // READ
    // ==========================

    @Test
    void shouldReturnAmenityById() throws Exception {
        String location = mockMvc.perform(post(BASE)
                .contentType(APPLICATION_JSON)
                .content("""
                        {
                          "name": "Temp",
                          "description": "Valid description"
                        }
                        """))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getHeader("Location");

        assertNotNull(location);

        String id = location.substring(location.lastIndexOf("/") + 1);

        mockMvc.perform(get(BASE + "/" + id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Temp"));
    }

    @Test
    void shouldReturnNotFoundForInvalidId() throws Exception {
        mockMvc.perform(get(BASE + "/999999"))
                .andExpect(status().isNotFound());
    }

    // ==========================
    // SEARCH
    // ==========================

    @Test
    void shouldSearchAmenityByName() throws Exception {
        mockMvc.perform(get(BASE + "/search/findByNameContainingIgnoreCase")
                .param("name", "pool"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.amenities", not(empty())));
    }

    @Test
    void shouldReturnEmptyWhenSearchNotFound() throws Exception {
        mockMvc.perform(get(BASE + "/search/findByNameContainingIgnoreCase")
                .param("name", "xyz_not_exist"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.amenities", hasSize(0)));
    }

    // ==========================
    // PROJECTION
    // ==========================

    @Test
    void shouldReturnProjection() throws Exception {
        mockMvc.perform(get(BASE)
                .param("projection", "amenitySummary"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.amenities[0].amenityId").exists())
                .andExpect(jsonPath("$._embedded.amenities[0].name").exists());
    }
}