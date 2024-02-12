package com.techriseyou.taskmanager.service;

/**
 * Description: TaskServiceTest.
 * Author: Naveen
 */

import com.techriseyou.taskmanager.entity.Task;
import com.techriseyou.taskmanager.repository.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskService taskService;

    private Task task1;
    private Task task2;

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
    }

    @Test
    void getAllTasks() {
        when(taskRepository.findAll()).thenReturn(Arrays.asList(task1, task2));
        List<Task> tasks = taskService.getAllTasks();
        assertNotNull(tasks);
        assertEquals(2, tasks.size());
        verify(taskRepository, times(1)).findAll();
    }

    @Test
    void getTaskById() {
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task1));
        Optional<Task> foundTask = taskService.getTaskById(1L);
        assertTrue(foundTask.isPresent());
        assertEquals(task1.getTitle(), foundTask.get().getTitle());
        verify(taskRepository, times(1)).findById(1L);
    }

    @Test
    void createTask() {
        when(taskRepository.save(any(Task.class))).thenReturn(task1);
        Task savedTask = taskService.createTask(task1);
        assertNotNull(savedTask);
        assertEquals(task1.getTitle(), savedTask.getTitle());
        verify(taskRepository, times(1)).save(task1);
    }

    @Test
    void updateTask_ExistingId() {
        when(taskRepository.existsById(1L)).thenReturn(true);
        when(taskRepository.save(any(Task.class))).thenReturn(task1);
        Task updatedTask = taskService.updateTask(1L, task1);
        assertNotNull(updatedTask);
        assertEquals(task1.getId(), updatedTask.getId());
        verify(taskRepository).save(task1);
    }

    @Test
    void updateTask_NonExistingId() {
        when(taskRepository.existsById(1L)).thenReturn(false);
        Task updatedTask = taskService.updateTask(1L, task1);
        assertNull(updatedTask);
        verify(taskRepository, never()).save(any(Task.class));
    }

    @Test
    void deleteTask() {
        doNothing().when(taskRepository).deleteById(1L);
        taskService.deleteTask(1L);
        verify(taskRepository, times(1)).deleteById(1L);
    }
}

