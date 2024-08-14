package by.ak.todo_restapi_app.dto;

import by.ak.todo_restapi_app.entity.Task;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;
@Builder
public record TasksListForUpdatingDTO(
        String description,
        List<Task> tasks,
        LocalDateTime dateTimeOfCreation,
        boolean isComplete) {
}
