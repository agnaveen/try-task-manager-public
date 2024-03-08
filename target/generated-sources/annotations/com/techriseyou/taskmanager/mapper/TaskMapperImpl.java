package com.techriseyou.taskmanager.mapper;

import com.techriseyou.taskmanager.dto.TaskDto;
import com.techriseyou.taskmanager.entity.Task;
import com.techriseyou.taskmanager.entity.User;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-03-08T19:18:04+0530",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 17 (Oracle Corporation)"
)
@Component
public class TaskMapperImpl implements TaskMapper {

    @Override
    public TaskDto taskToTaskDTO(Task task) {
        if ( task == null ) {
            return null;
        }

        TaskDto taskDto = new TaskDto();

        taskDto.setAssignedToId( taskAssignedToId( task ) );
        taskDto.setCreatedById( taskCreatedById( task ) );
        taskDto.setId( task.getId() );
        taskDto.setTitle( task.getTitle() );
        taskDto.setDescription( task.getDescription() );
        taskDto.setDueDate( task.getDueDate() );
        taskDto.setPriority( task.getPriority() );
        taskDto.setStatus( task.getStatus() );

        return taskDto;
    }

    @Override
    public Task taskDTOToTask(TaskDto taskDTO) {
        if ( taskDTO == null ) {
            return null;
        }

        Task task = new Task();

        task.setAssignedTo( taskDtoToUser( taskDTO ) );
        task.setCreatedBy( taskDtoToUser1( taskDTO ) );
        task.setId( taskDTO.getId() );
        task.setTitle( taskDTO.getTitle() );
        task.setDescription( taskDTO.getDescription() );
        task.setDueDate( taskDTO.getDueDate() );
        task.setPriority( taskDTO.getPriority() );
        task.setStatus( taskDTO.getStatus() );

        return task;
    }

    private Long taskAssignedToId(Task task) {
        if ( task == null ) {
            return null;
        }
        User assignedTo = task.getAssignedTo();
        if ( assignedTo == null ) {
            return null;
        }
        Long id = assignedTo.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private Long taskCreatedById(Task task) {
        if ( task == null ) {
            return null;
        }
        User createdBy = task.getCreatedBy();
        if ( createdBy == null ) {
            return null;
        }
        Long id = createdBy.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    protected User taskDtoToUser(TaskDto taskDto) {
        if ( taskDto == null ) {
            return null;
        }

        User user = new User();

        user.setId( taskDto.getAssignedToId() );

        return user;
    }

    protected User taskDtoToUser1(TaskDto taskDto) {
        if ( taskDto == null ) {
            return null;
        }

        User user = new User();

        user.setId( taskDto.getCreatedById() );

        return user;
    }
}
