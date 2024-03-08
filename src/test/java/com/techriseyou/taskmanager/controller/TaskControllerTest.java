package com.techriseyou.taskmanager.controller;

/**
 * Description: TaskControllerTest.
 * Author: Naveen
 */

import com.techriseyou.taskmanager.dto.TaskDto;
import com.techriseyou.taskmanager.entity.Task;
import com.techriseyou.taskmanager.service.TaskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TaskControllerTest {

    @Mock
    private TaskService taskService;

    @InjectMocks
    private TaskController taskController;

    private Task task1, task2;
    private TaskDto taskDto1, taskDto2;

    @BeforeEach
    void setUp() {
        task1 = new Task();
        task1.setId(1L);
        task1.setTitle("Task 1");
        task1.setDescription("Description 1");

        task2 = new Task();
        task2.setId(2L);
        task2.setTitle("Task 2");
        task2.setDescription("Description 2");

        taskDto1 = new TaskDto();
        taskDto1.setId(1L);
        taskDto1.setTitle("Task 1");
        taskDto1.setDescription("Description 1");

        taskDto2 = new TaskDto();
        taskDto2.setId(2L);
        taskDto2.setTitle("Task 2");
        taskDto2.setDescription("Description 2");
    }

    @Test
    void getAllTasks() {
        when(taskService.getAllTasks()).thenReturn(Arrays.asList(taskDto1, taskDto2));
        List<TaskDto> tasks = taskController.getAllTasks();
        assertEquals(2, tasks.size());
        verify(taskService).getAllTasks();
    }

    @Test
    void getTaskById_ExistingId() {
        when(taskService.getTaskById(1L)).thenReturn(Optional.of(task1));
        Task result = taskController.getTaskById(1L);
        assertEquals(task1, result);
        verify(taskService).getTaskById(1L);
    }

    @Test
    void getTaskById_NonExistingId() {
        when(taskService.getTaskById(any(Long.class))).thenReturn(Optional.empty());
        Task result = taskController.getTaskById(999L);
        assertNull(result);
        verify(taskService).getTaskById(999L);
    }

    @Test
    void createTask() {
        when(taskService.createTask(any(Task.class))).thenReturn(taskDto1);
        TaskDto result = taskController.createTask(task1);
        assertEquals(task1, result);
        verify(taskService).createTask(task1);
    }

    @Test
    void updateTask_ExistingId() {
        when(taskService.updateTask(eq(1L), any(Task.class))).thenReturn(task1);
        Task result = taskController.updateTask(1L, task1);
        assertEquals(task1, result);
        verify(taskService).updateTask(eq(1L), any(Task.class));
    }

    @Test
    void deleteTask() {
        doNothing().when(taskService).deleteTask(1L);
        taskController.deleteTask(1L);
        verify(taskService).deleteTask(1L);
    }
}
