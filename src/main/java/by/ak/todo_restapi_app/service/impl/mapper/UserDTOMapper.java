package by.ak.todo_restapi_app.service.impl.mapper;

import by.ak.todo_restapi_app.dto.UserDTO;
import by.ak.todo_restapi_app.entity.User;
import by.ak.todo_restapi_app.service.DTOMapper;
import org.springframework.stereotype.Service;

@Service
public class UserDTOMapper implements DTOMapper<User, UserDTO> {
    @Override
    public User toEntity(UserDTO dto) {
        User user = new User();
        user.setId(dto.id()); // May be null
        user.setUsername(dto.username());
        user.setPassword(dto.password());
        return user;
    }

    @Override
    public UserDTO toDto(User entity) {
        return UserDTO.builder()
                .id(entity.getId())
                .username(entity.getUsername())
                .password(entity.getPassword())
                .build();
    }
}
