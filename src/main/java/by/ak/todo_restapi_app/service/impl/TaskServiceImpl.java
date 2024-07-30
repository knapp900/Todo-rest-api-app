package by.ak.todo_restapi_app.service.impl;

import by.ak.todo_restapi_app.entity.Status;
import by.ak.todo_restapi_app.entity.Task;
import by.ak.todo_restapi_app.entity.TasksList;
import by.ak.todo_restapi_app.exceptions.customException.TaskNotFoundException;
import by.ak.todo_restapi_app.exceptions.customException.TaskServiceException;
import by.ak.todo_restapi_app.repository.TaskRepository;
import by.ak.todo_restapi_app.service.TaskService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Objects;

@RequiredArgsConstructor
@Slf4j
@Service
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;
    private final TasksListServiceImpl tasksListService;

    @Override
    public void deleteTask(Long tasksListId, Long taskId) {
        try {
            this.taskRepository.findTaskByTasksListIdAndId(tasksListId,taskId).orElseThrow(() -> {
                log.error("Error task not found with id: {}", taskId);
                return new TaskNotFoundException(String.format("Task with id: %d not found.", taskId));
            });

            this.taskRepository.deleteById(taskId);

        } catch (Exception e) {
            log.error("Deleting task with id: {} failed ", taskId, e);
            throw new TaskServiceException(String.format("Deleting task with id: %d failed. ", taskId), e);
        }
    }

    @Override
    @Transactional
    public void moveTaskToAnotherTasksList(
            Long sourceTasksListId,
            Long sourceTaskId,
            Long destTasksListId,
            String username) {
        try {
            Task taskForMoving = this.taskRepository.findById(sourceTaskId).orElseThrow(() -> {
                log.error("Error task with id:{} not found", sourceTaskId);
                return new TaskNotFoundException(String.format("Task with id: %d not found.", sourceTaskId));
            });
            TasksList destTasksList = this.tasksListService.getTasksListByUsernameAndId(destTasksListId, username);
            TasksList sourceTaskList = this.tasksListService.getTasksListByUsernameAndId(sourceTasksListId, username);

            if (Objects.equals(taskForMoving.getTasksList(),sourceTaskList)){

                taskForMoving.setTasksList(destTasksList);
            } else {
                throw new TaskServiceException(String.format(
                        "Moving task with id: %d to list with id: %d failed.", sourceTaskId, destTasksListId));
            }

        } catch (Exception e) {
            log.error("Error from moving task with id:{} to another list with id: {}.", sourceTaskId, destTasksListId);
            throw new TaskServiceException(
                    String.format(
                            "Moving task with id: %d to list with id: %d failed.", sourceTaskId, destTasksListId), e);
        }
    }

    @Override
    public Task createTask(Long tasksListId, Task task,String username) {
        try {
            task.setTasksList(this.tasksListService.getTasksListByUsernameAndId(tasksListId, username));
            return this.taskRepository.save(task);
        } catch (Exception e) {
            log.error("Error creating task: {}.", task, e);
            throw new TaskServiceException("Creating task failed.");
        }
    }

    @Override
    public Task getTask(Long tasksListId, Long taskId) {
        try {
            return this.taskRepository.findTaskByTasksListIdAndId(tasksListId, taskId).orElseThrow(() -> {
                log.error("Error task not found with id: {}", taskId);
                return new TaskNotFoundException(String.format("Task with id: %d not found.", taskId));
            });
        } catch (Exception e) {
            log.error("Error fetching task with id: {}.", taskId, e);
            throw new TaskServiceException(String.format("Fetching task with id: %d failed.", taskId));
        }
    }

    @Override
    public Task updateTask(Long tasksListId, Long taskId, Task task) {
        try {
            Task taskForUpdate = this.taskRepository.findTaskByTasksListIdAndId(tasksListId, taskId).orElseThrow(() -> {
                log.error("Error task not found with id: {}", taskId);
                return new TaskNotFoundException(String.format("Task with id: %d not found.", taskId));
            });

            taskForUpdate.setTitle(Objects.nonNull(task.getTitle())?task.getTitle():taskForUpdate.getTitle());
            taskForUpdate.setContent(Objects.nonNull(task.getContent())?task.getContent():taskForUpdate.getContent());
            taskForUpdate.setImportance(Objects.nonNull(task.getImportance())?task.getImportance():taskForUpdate.getImportance());
            taskForUpdate.setStatus(Objects.nonNull(task.getStatus())?task.getStatus():taskForUpdate.getStatus());
            taskForUpdate.setCreatedDateTime(Objects.nonNull(task.getCreatedDateTime())?task.getCreatedDateTime():taskForUpdate.getCreatedDateTime());
            taskForUpdate.setLastModifiedDateTime(LocalDateTime.now());

         return this.taskRepository.save(taskForUpdate);

        } catch (Exception e) {
            log.error("Error updating task with id: {}.", taskId, e);
            throw new TaskServiceException(String.format("Updating task with id: %d failed.", taskId));
        }
    }

    @Override
    public Page<Task> getAllTasks(Long tasksListId, Pageable pageable) {
        try {
            return this.taskRepository.findAllByTasksListId(tasksListId,pageable);
        } catch (Exception e){
            log.error("Error fetching all tasks.", e);
            throw new TaskServiceException("Fetching all tasks failed.");
        }
    }


    @Override
    public Page<Task> getAllByStatus(Status status, Long tasksListId, Pageable pageable) {

        try {
            return this.taskRepository.findByStatus(status,tasksListId,pageable);
        } catch (Exception e){
            log.error("Error fetching all tasks with status: {}.",status, e);
            throw new TaskServiceException(String.format("Fetching all tasks with status: %s failed.",status));
        }
    }


}
