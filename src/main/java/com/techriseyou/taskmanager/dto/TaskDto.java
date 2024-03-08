package com.techriseyou.taskmanager.dto;

/**
 * Description: TaskDto.
 * Author: Naveen
 */
import com.techriseyou.taskmanager.enums.TaskStatus;
import lombok.Data;

import java.util.Date;

@Data
public class TaskDto {

    private Long id;
    private String title;
    private String description;
    private Date dueDate;
    private int priority;
    private Long assignedToId;
    private Long createdById;
    private TaskStatus status;

    // Constructors, Getters, and Setters
    public TaskDto() {
    }

}

