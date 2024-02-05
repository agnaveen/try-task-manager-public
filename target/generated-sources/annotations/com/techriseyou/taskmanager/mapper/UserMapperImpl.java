package com.techriseyou.taskmanager.mapper;

import com.techriseyou.taskmanager.dto.UserDTO;
import com.techriseyou.taskmanager.entity.User;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-02-05T06:01:48+0530",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 17 (Oracle Corporation)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public UserDTO toDto(User user) {
        if ( user == null ) {
            return null;
        }

        UserDTO userDTO = new UserDTO();

        if ( user.getId() != null ) {
            userDTO.setId( user.getId().intValue() );
        }
        userDTO.setUsername( user.getUsername() );

        return userDTO;
    }

    @Override
    public User toEntity(UserDTO userDto) {
        if ( userDto == null ) {
            return null;
        }

        User user = new User();

        if ( userDto.getId() != null ) {
            user.setId( userDto.getId().longValue() );
        }
        user.setUsername( userDto.getUsername() );

        return user;
    }

    @Override
    public List<UserDTO> toDtoList(List<User> users) {
        if ( users == null ) {
            return null;
        }

        List<UserDTO> list = new ArrayList<UserDTO>( users.size() );
        for ( User user : users ) {
            list.add( toDto( user ) );
        }

        return list;
    }

    @Override
    public List<User> toEntityList(List<UserDTO> userDtos) {
        if ( userDtos == null ) {
            return null;
        }

        List<User> list = new ArrayList<User>( userDtos.size() );
        for ( UserDTO userDTO : userDtos ) {
            list.add( toEntity( userDTO ) );
        }

        return list;
    }
}
