package by.ak.todo_restapi_app.service;

import by.ak.todo_restapi_app.entity.TasksList;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TasksListService {
    //TODO (Medium) Add docs to all methods
    void deleteTasksList(Long id,String username);

    TasksList createTasksList(String description, String username);

    TasksList updateTasksList(Long id, String description,String username);

    TasksList getTasksListByUsernameAndId(Long id, String username);

    Page<TasksList> getAllTasksLists(Pageable pageable, String username);

    Page<TasksList> getAllUncompletedTasksLists(Pageable pageable, String username);

    Page<TasksList> getAllCompletedTasksLists(Pageable pageable, String username);

}


