package by.ak.todo_restapi_app.service.impl.mapper;

import by.ak.todo_restapi_app.dto.TaskDTO;
import by.ak.todo_restapi_app.entity.Task;
import by.ak.todo_restapi_app.service.DTOMapper;
import org.springframework.stereotype.Service;

@Service

public class TaskDTOMapper implements DTOMapper<Task, TaskDTO> {
    @Override
    public Task toEntity(TaskDTO dto) {
        Task task = new Task();
        task.setId(dto.id());
        task.setTitle(dto.title());
        task.setContent(dto.content());
        task.setImportance(dto.importance());
        task.setStatus(dto.status());
        task.setCreatedDateTime(dto.createdDateTime());
        task.setLastModifiedDateTime(dto.lastModifiedDateTime());
        return task;
    }

    @Override
    public TaskDTO toDto(Task entity) {
        return TaskDTO.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .content(entity.getContent())
                .importance(entity.getImportance())
                .status(entity.getStatus())
                .createdDateTime(entity.getCreatedDateTime())
                .lastModifiedDateTime(entity.getLastModifiedDateTime())
                .build();
    }
}
