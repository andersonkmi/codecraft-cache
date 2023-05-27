package org.codecraftlabs.cache.controller.mk1;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.annotation.Nonnull;

import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class CacheControllerTests {
    private MockMvc mvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @BeforeEach
    void beforeEach() {
        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
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

    @Nonnull
    private JSONObject createUpsertResponse() {
        JSONObject response = new JSONObject();
        response.put("message", "Done");
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
        return request.toString();
    }
 }
