package by.ak.todo_restapi_app.service;

import by.ak.todo_restapi_app.dto.TasksListDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TasksListService {
    //TODO (Medium) Add docs to all methods
    void deleteTasksList(Long id,String username);

    TasksListDTO createTasksList(String description, String username);

    TasksListDTO updateTasksList(Long id, String description,String username);

    TasksListDTO getTasksListByUsernameAndId(Long id, String username);

    Page<TasksListDTO> getAllTasksLists(Pageable pageable, String username);

    Page<TasksListDTO> getAllUncompletedTasksLists(Pageable pageable, String username);

    Page<TasksListDTO> getAllCompletedTasksLists(Pageable pageable, String username);

}


