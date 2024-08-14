package by.ak.todo_restapi_app.service.impl;

import by.ak.todo_restapi_app.dto.TasksListDTO;
import by.ak.todo_restapi_app.dto.TasksListForUpdatingDTO;
import by.ak.todo_restapi_app.entity.TasksList;
import by.ak.todo_restapi_app.entity.User;
import by.ak.todo_restapi_app.exceptions.customException.TaskListNotFoundException;
import by.ak.todo_restapi_app.exceptions.customException.TasksListServiceException;
import by.ak.todo_restapi_app.exceptions.customException.UserNotFoundException;
import by.ak.todo_restapi_app.repository.TasksListRepository;
import by.ak.todo_restapi_app.repository.UserRepository;
import by.ak.todo_restapi_app.service.TasksListService;
import by.ak.todo_restapi_app.service.impl.mapper.TasksListMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
@Service
public class TasksListServiceImpl implements TasksListService {

    private final TasksListRepository tasksListRepository;
    private final UserRepository userRepository;

    private final TasksListMapper tasksListMapper;
    @Override
    public void deleteTasksList(Long id, String username) {

        try {
            this.userRepository.findByUsername(username)
                    .orElseThrow(() -> {
                        log.error("User not found with username: {}.", username);
                        return new UserNotFoundException(
                                String.format("User with username: %s not found.", username));
                    });

            this.tasksListRepository.deleteById(id);

        } catch (Exception e) {
            log.info("Error to deleting task list with id: {}.", id, e);
            throw new TasksListServiceException(
                    String.format("Deleting task list with id: %d failed", id));
        }

    }

    @Override
    public TasksListDTO createTasksList(String description, String username) {
        try {
            User user = this.userRepository.findByUsername(username)
                    .orElseGet(() -> {
                        User newUser = new User();
                        newUser.setUsername(username);
                        return this.userRepository.save(newUser);
                    });

            TasksList tasksList = new TasksList();
            tasksList.setDescription(description);
            tasksList.setTasks(Collections.emptyList());
            tasksList.setDateTimeOfCreation(LocalDateTime.now());
            tasksList.setUsername(user.getUsername());

            return tasksListMapper.toDto(this.tasksListRepository.save(tasksList));

        } catch (Exception e) {

            log.error("Error of creating task list for user: {}", username);
            throw new TasksListServiceException(
                    String.format("Creating task list for %s failed.", username));

        }
    }

    @Transactional
    @Override
    public TasksListDTO updateTasksList(Long id, TasksListForUpdatingDTO updates, String username) {
        try {
            TasksList tasksListForUpdating = this.tasksListRepository.findAllByUsernameAndId(username, id).orElseThrow(() -> {
                log.error("Task list not found with id: {}.", id);
                return new TaskListNotFoundException(
                        String.format("Task list with id: %d not found.", id));
            });

            tasksListForUpdating.setDescription(Objects.nonNull(updates.description())?updates.description():tasksListForUpdating.getDescription());
            tasksListForUpdating.setTasks(Objects.nonNull(updates.tasks())?updates.tasks():tasksListForUpdating.getTasks());
            tasksListForUpdating.setDateTimeOfCreation(Objects.nonNull(updates.dateTimeOfCreation())?updates.dateTimeOfCreation():tasksListForUpdating.getDateTimeOfCreation());
            tasksListForUpdating.setComplete(updates.isComplete());

            return tasksListMapper.toDto(this.tasksListRepository.save(tasksListForUpdating));

        } catch (Exception e) {
            log.error("Error updating task list with id: {}.", id);
            throw new TasksListServiceException(
                    String.format("Updating task list with id: %d failed.", id));
        }

    }

    @Override
    public TasksListDTO getTasksListByUsernameAndId(Long id, String username) {
        try {
            return tasksListMapper.toDto(this.tasksListRepository.findAllByUsernameAndId(username, id).orElseThrow(() -> {
                log.error("Error task list with id: {} not found", id);
                return new TaskListNotFoundException(String.format("Task list with id: %d nut found.",id));
            }));

        } catch (Exception e) {
            log.error("Error fetching task list by id:{}", id, e);
            throw new TasksListServiceException(String.format(
                    "Fetching task list by id: %s failed or you don't have rights for this..", id));
        }
    }

    @Override
    public Page<TasksListDTO> getAllTasksLists(Pageable pageable, String username) {
        try {
            return this.tasksListRepository.findAllByUsername(username, pageable)
                    .map(tasksListMapper::toDto);

        } catch (Exception e) {
            log.error("Error fetching all task lists for user: {}.", username);
            throw new TasksListServiceException(
                    String.format("Fetching all task lists for user: %s failed.",username));
        }
    }

    @Override
    public Page<TasksListDTO> getAllUncompletedTasksLists(Pageable pageable, String username) {
        try {
            return this.tasksListRepository.findAllByUsernameAndIsCompleteFalse(username, pageable)
                    .map(tasksListMapper::toDto);

        } catch (Exception e) {
            log.error("Error fetching all uncompleted task lists for user: {}.", username);
            throw new TasksListServiceException(
                    String.format("Fetching all uncompleted task lists for user: %s failed.",username));
        }
    }

    @Override
    public Page<TasksListDTO> getAllCompletedTasksLists(Pageable pageable, String username) {
        try {
            return this.tasksListRepository.findAllByUsernameAndIsCompleteTrue(username, pageable)
                    .map(tasksListMapper::toDto);

        } catch (Exception e) {
            log.error("Error fetching all completed task lists for user: {}.", username);
            throw new TasksListServiceException(
                    String.format("Fetching all completed task lists for user: %s failed.",username));
        }
    }

}
