package com.techriseyou.taskmanager.mapper;

/**
 * Description: TaskMapper.
 * Author: Naveen
 */
import com.techriseyou.taskmanager.dto.TaskDto;
import com.techriseyou.taskmanager.entity.Task;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TaskMapper {

    TaskMapper INSTANCE = Mappers.getMapper(TaskMapper.class);

    @Mapping(source = "assignedTo.id", target = "assignedToId")
    @Mapping(source = "createdBy.id", target = "createdById")
    TaskDto taskToTaskDTO(Task task);

    @Mapping(source = "assignedToId", target = "assignedTo.id")
    @Mapping(source = "createdById", target = "createdBy.id")
    Task taskDTOToTask(TaskDto taskDTO);
}
