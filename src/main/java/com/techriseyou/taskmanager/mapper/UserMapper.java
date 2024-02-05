package com.techriseyou.taskmanager.mapper;

import com.techriseyou.taskmanager.dto.UserDTO;
import com.techriseyou.taskmanager.entity.User;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDTO toDto(User user);

    User toEntity(UserDTO userDto);

    List<UserDTO> toDtoList(List<User> users);

    List<User> toEntityList(List<UserDTO> userDtos);
}
