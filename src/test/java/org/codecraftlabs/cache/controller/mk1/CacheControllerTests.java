package org.codecraftlabs.cache.controller.mk1;

import org.json.JSONObject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import javax.annotation.Nonnull;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CacheControllerTests {
    @Autowired
    private MockMvc mvc;

    @Test
    @Order(0)
    @DisplayName("Test to verify creation of new cache item")
    public void insertCacheItemSuccessful() throws Exception {
        String requestBody = creteRequest("key001", Map.of("name", "Test Name", "id", "Test Id"));
        JSONObject response = createUpsertResponse();
        mvc.perform(put("/v1/cache")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8")
                        .accept(MediaType.APPLICATION_JSON)
                        .content(requestBody)
                )
                .andExpect(status().isOk())
                .andExpect(content().json(response.toString()));
    }

    @Test
    @Order(10)
    @DisplayName("Test to verify cache item update")
    public void updateCacheItemSuccessful() throws Exception {
        JSONObject response = createUpsertResponse();
        String requestBody1 = creteRequest("key002", Map.of("name", "Test Name 2", "id", "Test Id 2"));

        mvc.perform(put("/v1/cache")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8")
                        .accept(MediaType.APPLICATION_JSON)
                        .content(requestBody1)
                )
                .andExpect(status().isOk())
                .andExpect(content().json(response.toString()));

        String requestBody2 = creteRequest("key002", Map.of("name", "Test Name 3", "id", "Test Id 3"));
        mvc.perform(put("/v1/cache")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8")
                        .accept(MediaType.APPLICATION_JSON)
                        .content(requestBody2)
                )
                .andExpect(status().isOk())
                .andExpect(content().json(response.toString()));
    }

    @Test
    @Order(20)
    @DisplayName("Test to verify if the cache size is correct")
    public void testCacheSizeRetrieval() throws Exception {
        JSONObject firstResponse = createGetSizeResponse(2);
        mvc.perform(get("/v1/cache/size")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8")
                .accept(MediaType.APPLICATION_JSON)
            )
                .andExpect(status().isOk())
                .andExpect(content().json(firstResponse.toString()));

        JSONObject response = createUpsertResponse();
        String requestBody = creteRequest("key003", Map.of("name", "Test Name 3", "id", "Test Id 3"));
        mvc.perform(put("/v1/cache")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8")
                        .accept(MediaType.APPLICATION_JSON)
                        .content(requestBody)
                )
                .andExpect(status().isOk())
                .andExpect(content().json(response.toString()));

        JSONObject secondResponse = createGetSizeResponse(3);
        mvc.perform(get("/v1/cache/size")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8")
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(content().json(secondResponse.toString()));
    }

    @Test
    @Order(30)
    @DisplayName("Test to verify the delete operation")
    public void deleteCacheItem() throws Exception {
        mvc.perform(delete("/v1/cache/key003")
                .characterEncoding("utf-8")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @Order(30)
    @DisplayName("Test to verify the delete operation for non existing item")
    public void deleteNonExistingCacheItem() throws Exception {
        mvc.perform(delete("/v1/cache/key999")
                        .characterEncoding("utf-8")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Nonnull
    private JSONObject createUpsertResponse() {
        JSONObject response = new JSONObject();
        response.put("message", "Done");
        return response;
    }

    @Nonnull
    private JSONObject createGetSizeResponse(int expectedSize) {
        JSONObject response = new JSONObject();
        response.put("message", "Current cache size");
        response.put("size", expectedSize);
        return response;
    }

    @Nonnull
    private String creteRequest(@Nonnull String key, @Nonnull Map<String, String> values) {
        JSONObject cacheValue = new JSONObject();
        for (Map.Entry<String, String> value : values.entrySet()) {
            cacheValue.put(value.getKey(), value.getValue());
        }
        JSONObject request = new JSONObject();
        request.put("key", key);
        request.put("value", cacheValue.toString());
        request.put("versionId", System.currentTimeMillis());
        return request.toString();
    }
 }
