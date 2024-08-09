package by.ak.todo_restapi_app.service;

import by.ak.todo_restapi_app.dto.TaskDTO;
import by.ak.todo_restapi_app.entity.Status;
import by.ak.todo_restapi_app.entity.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TaskService {
    //TODO (Medium) Add docs to all methods
    void deleteTask(Long tasksListId, Long taskId, String username);

    void moveTaskToAnotherTasksList(Long sourceTasksListId, Long sourceTaskId, Long destTasksListId, String username);

    TaskDTO createTask(Long tasksListId, TaskDTO task, String username);

    TaskDTO getTask(Long tasksListId, Long taskId);

    TaskDTO updateTask(Long tasksListId, Long taskId, TaskDTO task);

    Page<TaskDTO> getAllTasks(Long tasksListId, Pageable pageable);

    Page<TaskDTO> getAllByStatus(Status status, Long tasksListId, Pageable pageable);
}
