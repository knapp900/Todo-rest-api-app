package by.ak.todo_restapi_app.service;

import by.ak.todo_restapi_app.entity.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TaskService {
//TODO (Medium) Add docs to all methods
    void deleteTask(Long tasksListId,Long id);
    void moveTaskToAnotherTasksList(Long sourceTasksListId,Long sourceTaskId,Long destTasksListId,String username);
    Task createTask(Long tasksListId,Task task);
    Task getTask(Long tasksListId,Long taskId);
    Task updateTask(Long tasksListId,Long taskId,Task task);
    Page<Task> getAllTasks(Long tasksListId,Pageable pageable);
    Page<Task> getAllCompletedTasks(Long tasksListId,Pageable pageable);
    Page<Task> getAllUncompletedTasks(Long tasksListId,Pageable pageable);
}
