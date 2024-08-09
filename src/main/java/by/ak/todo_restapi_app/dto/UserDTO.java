package by.ak.todo_restapi_app.dto;

import lombok.Builder;

@Builder
public record UserDTO(
        Long id,
        String username,
        String password) {
}
