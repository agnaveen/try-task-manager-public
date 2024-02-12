package com.techriseyou.taskmanager.controller;

/**
 * Description: TaskControllerIntegrationTest.
 * Author: Naveen
 */

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.techriseyou.taskmanager.entity.Task;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class TaskControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    private String authToken;

    @BeforeEach
    void setUp() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        String loginData = objectMapper.writeValueAsString(Map.of(
                "username", "naveen@techriseyou.com",
                "password", "try@1234"
        ));

        MvcResult result = mockMvc.perform(post("/user/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(loginData))
                .andExpect(status().isOk())
                .andReturn();

        String responseString = result.getResponse().getContentAsString();
        Map<String, Object> responseMap = objectMapper.readValue(responseString, new TypeReference<>() {});
        authToken = "Bearer " + responseMap.get("data");

        // Initialize test data or any setup here if needed
    }

    @Test
    public void getAllTasks_WithAuthToken() throws Exception {
        mockMvc.perform(get("/task")
                        .header("Authorization", authToken))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    public void getTaskById_WithAuthToken() throws Exception {
        // Replace "1" with a valid task ID after ensuring it exists
        mockMvc.perform(get("/task/{id}", 1)
                        .header("Authorization", authToken))
                .andExpect(status().isOk());
    }

    @Test
    public void createTask_WithAuthToken() throws Exception {
        Task newTask = new Task();
        newTask.setTitle("New Task for Integration Test");
        newTask.setDescription("Description for Integration Test Task");

        ObjectMapper objectMapper = new ObjectMapper();
        String taskJson = objectMapper.writeValueAsString(newTask);

        mockMvc.perform(post("/task")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(taskJson)
                        .header("Authorization", authToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value(newTask.getTitle()))
                .andExpect(jsonPath("$.description").value(newTask.getDescription()));
    }

    @Test
    public void updateTask_WithAuthToken() throws Exception {
        Task updatedTask = new Task();
        updatedTask.setId(1L); // Ensure this ID exists and is correct
        updatedTask.setTitle("Updated Task for Integration Test");
        updatedTask.setDescription("Updated Description for Integration Test Task");

        ObjectMapper objectMapper = new ObjectMapper();
        String taskJson = objectMapper.writeValueAsString(updatedTask);

        mockMvc.perform(put("/task/{id}", updatedTask.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(taskJson)
                        .header("Authorization", authToken))
                .andExpect(status().isOk());
    }

    @Test
    public void deleteTask_WithAuthToken() throws Exception {
        // Replace "1" with a valid task ID to ensure it exists and can be deleted
        mockMvc.perform(delete("/task/{id}", 1)
                        .header("Authorization", authToken))
                .andExpect(status().isOk());
    }
}
