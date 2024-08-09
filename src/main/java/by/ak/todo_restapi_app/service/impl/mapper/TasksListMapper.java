package by.ak.todo_restapi_app.service.impl.mapper;

import by.ak.todo_restapi_app.dto.TasksListDTO;
import by.ak.todo_restapi_app.entity.TasksList;
import by.ak.todo_restapi_app.service.DTOMapper;
import org.springframework.stereotype.Service;

@Service
public class TasksListMapper implements DTOMapper<TasksList, TasksListDTO> {
    @Override
    public TasksList toEntity(TasksListDTO dto) {
        TasksList tasksList = new TasksList();
        tasksList.setId(dto.id());
        tasksList.setDescription(dto.description());
        tasksList.setDateTimeOfCreation(dto.dateTimeOfCreation());
        tasksList.setComplete(dto.isComplete());
        tasksList.setUsername(dto.username());
        tasksList.setTasks(dto.tasks());
        return tasksList;
    }

    @Override
    public TasksListDTO toDto(TasksList entity) {
        return TasksListDTO.builder()
                .id(entity.getId())
                .description(entity.getDescription())
                .dateTimeOfCreation(entity.getDateTimeOfCreation())
                .isComplete(entity.isComplete())
                .username(entity.getUsername())
                .tasks(entity.getTasks())
                .build();
    }
}
