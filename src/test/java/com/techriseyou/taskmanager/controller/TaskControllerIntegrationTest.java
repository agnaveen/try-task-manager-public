package com.techriseyou.taskmanager.controller;

/**
 * Description: TaskControllerIntegrationTest.
 * Author: Naveen
 */

import com.techriseyou.taskmanager.repository.TaskRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class TaskControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TaskRepository taskRepository;

    @Test
    public void getAllTasksIntegrationTestForbiddenWithoutToken() throws Exception {
        mockMvc.perform(get("/task"))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$").isArray());
    }
}

