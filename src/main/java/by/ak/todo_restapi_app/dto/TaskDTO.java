package by.ak.todo_restapi_app.dto;

import by.ak.todo_restapi_app.entity.Importance;
import by.ak.todo_restapi_app.entity.Status;
import lombok.Builder;

import java.time.LocalDateTime;
@Builder
public record TaskDTO(
        Long id,
        Long tasksListId,
        String title,
        String content,
        Importance importance,
        Status status,
        LocalDateTime createdDateTime,
        LocalDateTime lastModifiedDateTime) {
}
