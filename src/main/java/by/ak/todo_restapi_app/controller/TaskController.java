package by.ak.todo_restapi_app.controller;

import by.ak.todo_restapi_app.service.JwtService;
import by.ak.todo_restapi_app.service.TaskService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/lists/{tasksListId}/tasks")
public class TaskController {


    private final TaskService taskService;

    private final JwtService jwtService;


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
            @RequestHeader("Authorization") String token) {
        String username = this.jwtService.extractUsername(token);
        log.info("Moving task id: {} with tasks list id: {} in to tasks list id: {}.",
                sourceTaskId,
                tasksListId,
                destTasksListId);

        this.taskService.moveTaskToAnotherTasksList(tasksListId, sourceTaskId, destTasksListId, username);

        log.info("Task id: {} with tasks list id: {} in to tasks list id: {} moved.",
                sourceTaskId,
                tasksListId,
                destTasksListId);

        return ResponseEntity.noContent().build();



    }



}
