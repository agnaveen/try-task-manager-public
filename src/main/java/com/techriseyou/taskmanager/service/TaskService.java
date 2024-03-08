package com.techriseyou.taskmanager.service;

/**
 * Description: TaskService.
 * Author: Naveen
 */
import com.techriseyou.taskmanager.core.RequestContext;
import com.techriseyou.taskmanager.dto.TaskDto;
import com.techriseyou.taskmanager.entity.Task;
import com.techriseyou.taskmanager.mapper.TaskMapper;
import com.techriseyou.taskmanager.repository.TaskRepository;
import com.techriseyou.taskmanager.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;
    private final UserRepository userRepository;

    @Autowired
    public TaskService(TaskRepository taskRepository, TaskMapper taskMapper, UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.taskMapper = taskMapper;
        this.userRepository = userRepository;
    }

    public List<TaskDto> getAllTasks() {
        List<Task> tasks = taskRepository.findByAssignedTo_id(RequestContext.getCurrentUserId());
        return tasks.stream().map(e -> taskMapper.taskToTaskDTO(e)).collect(Collectors.toList());
    }

    public Optional<Task> getTaskById(Long id) {
        return taskRepository.findById(id);
    }

    public TaskDto createTask(Task task) {
        task.setAssignedTo(RequestContext.getCurrentUser());
        task = taskRepository.save(task);
        return taskMapper.taskToTaskDTO(task);
    }

    public Task updateTask(Long id, Task updatedTask) {
        if (taskRepository.existsById(id)) {
            updatedTask.setId(id);
            return taskRepository.save(updatedTask);
        }
        return null; // Task with the given ID does not exist
    }

    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }
}

