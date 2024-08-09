package by.ak.todo_restapi_app.service.impl;

import by.ak.todo_restapi_app.dto.TaskDTO;
import by.ak.todo_restapi_app.dto.TasksListDTO;
import by.ak.todo_restapi_app.entity.Importance;
import by.ak.todo_restapi_app.entity.Status;
import by.ak.todo_restapi_app.entity.Task;
import by.ak.todo_restapi_app.exceptions.customException.TaskNotFoundException;
import by.ak.todo_restapi_app.exceptions.customException.TaskServiceException;
import by.ak.todo_restapi_app.repository.TaskRepository;
import by.ak.todo_restapi_app.service.TaskService;
import by.ak.todo_restapi_app.service.impl.mapper.TaskDTOMapper;
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

    private final TaskDTOMapper taskDTOMapper;
    private final static String DEFAULT_TITLE = "Title is blank!";
    private final static String DEFAULT_CONTENT = "Content is blank!";


    @Override
    public void deleteTask(Long tasksListId, Long taskId, String username) {
        try {
            this.taskRepository.findTaskByTasksListIdAndId(tasksListId, taskId).orElseThrow(() -> {
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
            TasksListDTO destTasksList = this.tasksListService.getTasksListByUsernameAndId(destTasksListId, username);
            TasksListDTO sourceTaskList = this.tasksListService.getTasksListByUsernameAndId(sourceTasksListId, username);

            if (Objects.equals(taskForMoving.getTasksListId(), sourceTaskList.id())) {

                taskForMoving.setTasksListId(destTasksList.id());
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
    @Transactional
    public TaskDTO createTask(Long tasksListId, TaskDTO taskDTO, String username) {
        try {
            TasksListDTO tasksList = this.tasksListService.getTasksListByUsernameAndId(tasksListId, username);
            Task task = taskDTOMapper.toEntity(taskDTO);
            task.setTitle(Objects.nonNull(task.getTitle()) ? task.getTitle() : DEFAULT_TITLE);
            task.setContent(Objects.nonNull(task.getContent()) ? task.getContent() : DEFAULT_CONTENT);
            task.setTasksListId(tasksList.id());
            task.setStatus(Objects.nonNull(task.getStatus()) ? task.getStatus() : Status.NOT_STARTED);
            task.setImportance(Objects.nonNull(task.getImportance()) ? task.getImportance() : Importance.LOW);
            task.setCreatedDateTime(LocalDateTime.now());
            task.setLastModifiedDateTime(LocalDateTime.now());
            return taskDTOMapper.toDto(this.taskRepository.save(task));
        } catch (Exception e) {
            log.error("Error creating task: {}.", taskDTO, e);
            throw new TaskServiceException("Creating task failed.");
        }
    }

    @Override
    public TaskDTO getTask(Long tasksListId, Long taskId) {
        try {
            return taskDTOMapper.toDto(this.taskRepository.findTaskByTasksListIdAndId(tasksListId, taskId).orElseThrow(() -> {
                log.error("Error task not found with id: {}", taskId);
                return new TaskNotFoundException(String.format("Task with id: %d not found.", taskId));
            }));
        } catch (Exception e) {
            log.error("Error fetching task with id: {}.", taskId, e);
            throw new TaskServiceException(String.format("Fetching task with id: %d failed.", taskId));
        }
    }

    @Override
    @Transactional
    public TaskDTO updateTask(Long tasksListId, Long taskId, TaskDTO task) {
        try {
            Task taskForUpdate = this.taskRepository.findTaskByTasksListIdAndId(tasksListId, taskId).orElseThrow(() -> {
                log.error("Error task not found with id: {}", taskId);
                return new TaskNotFoundException(String.format("Task with id: %d not found.", taskId));
            });

            taskForUpdate.setTitle(Objects.nonNull(task.title()) ? task.title() : taskForUpdate.getTitle());
            taskForUpdate.setContent(Objects.nonNull(task.content()) ? task.content() : taskForUpdate.getContent());
            taskForUpdate.setImportance(Objects.nonNull(task.importance()) ? task.importance() : taskForUpdate.getImportance());
            taskForUpdate.setStatus(Objects.nonNull(task.status()) ? task.status() : taskForUpdate.getStatus());
            taskForUpdate.setCreatedDateTime(Objects.nonNull(task.createdDateTime()) ? task.createdDateTime() : taskForUpdate.getCreatedDateTime());
            taskForUpdate.setLastModifiedDateTime(LocalDateTime.now());

            return taskDTOMapper.toDto(this.taskRepository.save(taskForUpdate));

        } catch (Exception e) {
            log.error("Error updating task with id: {}.", taskId, e);
            throw new TaskServiceException(String.format("Updating task with id: %d failed.", taskId));
        }
    }

    @Override
    public Page<TaskDTO> getAllTasks(Long tasksListId, Pageable pageable) {
        try {
            return this.taskRepository.findAllByTasksListId(tasksListId, pageable)
                    .map(taskDTOMapper::toDto);
        } catch (Exception e) {
            log.error("Error fetching all tasks.", e);
            throw new TaskServiceException("Fetching all tasks failed.");
        }
    }


    @Override
    public Page<TaskDTO> getAllByStatus(Status status, Long tasksListId, Pageable pageable) {

        try {
            return this.taskRepository.findByStatus(status, tasksListId, pageable)
                    .map(taskDTOMapper::toDto);
        } catch (Exception e) {
            log.error("Error fetching all tasks with status: {}.", status, e);
            throw new TaskServiceException(String.format("Fetching all tasks with status: %s failed.", status));
        }
    }


}
