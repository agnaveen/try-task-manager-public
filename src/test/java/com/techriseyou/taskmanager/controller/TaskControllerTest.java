package com.techriseyou.taskmanager.controller;

/**
 * Description: TaskControllerTest.
 * Author: Naveen
 */

import com.techriseyou.taskmanager.entity.Task;
import com.techriseyou.taskmanager.service.TaskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;

import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class TaskControllerTest {

    @Mock
    private TaskService taskService;

    @InjectMocks
    private TaskController taskController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(taskController).build();
    }

    @Test
    public void getAllTasksTest() throws Exception {
        when(taskService.getAllTasks()).thenReturn(Arrays.asList(new Task(), new Task()));

        mockMvc.perform(get("/task")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
               // .andExpect(jsonPath("$").isArray());

        verify(taskService, times(1)).getAllTasks();
    }

}

