package by.ak.todo_restapi_app.controller;

import by.ak.todo_restapi_app.entity.Status;
import by.ak.todo_restapi_app.entity.Task;
import by.ak.todo_restapi_app.service.TaskService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/auth/lists/{tasksListId}/tasks")
public class TaskController {


    private final TaskService taskService;


    @DeleteMapping("/{taskId}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long tasksListId, @PathVariable Long taskId) {
        log.info("Deleting task with id: {}.", taskId);
        this.taskService.deleteTask(tasksListId, taskId);
        log.info("Deleting task with id: {}.", taskId);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/move/{sourceTaskId}/to/{destTasksListId}")
    public ResponseEntity<Void> moveTaskToAnotherTasksList(
            @PathVariable Long tasksListId,
            @PathVariable Long sourceTaskId,
            @PathVariable Long destTasksListId,
            Principal principal) {

        log.info("Moving task id: {} with tasks list id: {} in to tasks list id: {}.",
                sourceTaskId,
                tasksListId,
                destTasksListId);

        this.taskService.moveTaskToAnotherTasksList(tasksListId, sourceTaskId, destTasksListId, principal.getName());

        log.info("Task id: {} with tasks list id: {} in to tasks list id: {} moved.",
                sourceTaskId,
                tasksListId,
                destTasksListId);

        return ResponseEntity.noContent().build();

    }

    @PostMapping
    public ResponseEntity<Task> createTask(
            @PathVariable Long tasksListId,
            @RequestBody Task task,
            Principal principal
    ) {
        log.info("Creating task for user: {} to task list with id: {}.", principal.getName(), tasksListId);
        Task createdTask = this.taskService.createTask(tasksListId, task, principal.getName());
        log.info("Task for user: {} to task list with id: {} created.", principal.getName(), tasksListId);

        return ResponseEntity.ok(createdTask);
    }

    @GetMapping("{taskId}")
    public ResponseEntity<Task> getTask(
            @PathVariable Long tasksListId,
            @PathVariable Long taskId
    ) {
        log.info("Fetching task with id: {} from task list with id: {}.", taskId, tasksListId);
        Task task = this.taskService.getTask(tasksListId, taskId);
        log.info("Task with id: {} from task list with id: {} fetched.", taskId, tasksListId);

        return ResponseEntity.ok(task);

    }

    @PatchMapping("{taskId}")
    public ResponseEntity<Task> updateTask(
            @PathVariable Long tasksListId,
            @PathVariable Long taskId,
            @RequestBody Task task
    ) {
        log.info("Updating task for user: {} to task list with id: {}", taskId, tasksListId);
        Task updatedTask = this.taskService.updateTask(tasksListId, taskId, task);
        log.info("Updating task for user: {} to task list with id: {}", taskId, tasksListId);

        return ResponseEntity.ok(updatedTask);
    }

    @GetMapping
    public ResponseEntity<Page<Task>> getAllTasks(
            @PathVariable Long tasksListId,
            Pageable pageable
    ) {
        log.info("Fetching all tasks from task list with id: {}.", tasksListId);
        Page<Task> targetTasks = this.taskService.getAllTasks(tasksListId, pageable);
        log.info("All tasks from task list with id: {} fetched.", tasksListId);

        return ResponseEntity.ok(targetTasks);
    }

    @GetMapping("/status")
    public ResponseEntity<Page<Task>> getAllTasksByStatus(
            @PathVariable Long tasksListId,
            @RequestBody Status status,
            Pageable pageable
    ) {
        log.info("Fetching all tasks when status: {} from task list with id: {}.", status, tasksListId);
        Page<Task> targetTasks = this.taskService.getAllByStatus(status, tasksListId, pageable);
        log.info("All tasks when status: {} from task list with id: {} fetched.", status, tasksListId);

        return ResponseEntity.ok(targetTasks);
    }

}
