package by.ak.todo_restapi_app.controller;

import by.ak.todo_restapi_app.entity.TasksList;
import by.ak.todo_restapi_app.service.JwtService;
import by.ak.todo_restapi_app.service.TasksListService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/lists")
public class TasksListController {

    private final TasksListService tasksListService;
    private final JwtService jwtService;

    @DeleteMapping("/{id}")
    ResponseEntity<Void> deleteTasksList(
            @RequestHeader("Authorization") String token,
            @PathVariable Long id) {
        String username = this.jwtService.extractUsername(token);
        log.info("Deleting task list with id: {}.", id);
        this.tasksListService.deleteTasksList(id,username);
        log.info("Task list with id: {} deleted.", id);
        return ResponseEntity.noContent().build();
    }


    @PostMapping
    public ResponseEntity<TasksList> createTasksList(
            @RequestHeader("Authorization") String token,
            @RequestBody String description) {
        String username = this.jwtService.extractUsername(token);
        log.info("Creating task list with user: {}.", username);
        TasksList tasksList = tasksListService.createTasksList(description,username);
        log.info("Created task list with user: {}.", username);
        return ResponseEntity.ok(tasksList);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TasksList> updateTasksList(
            @RequestHeader("Authorization") String token,
            @PathVariable Long id,
            @RequestBody String description) {
        String username = this.jwtService.extractUsername(token);
        log.info("Updating task list with id: {}.", id);
        this.tasksListService.updateTasksList(id, description,username);
        log.info("Task list with id: {} updated.", id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<TasksList> getTaskListById(
            @RequestHeader("Authorization") String token,
            @PathVariable Long id){
        String username = this.jwtService.extractUsername(token);
        log.info("Fetching task list by id: {}.",id);
        TasksList tasksLists = tasksListService.getTasksListByUsernameAndId(id,username);
        log.info("Task list fetched by id: {}",id);

        return ResponseEntity.ok(tasksLists);
    }

    @GetMapping
    public ResponseEntity<Page<TasksList>> getAllTasksLists(
            @RequestHeader("Authorization") String token,
            Pageable pageable) {
        String username = this.jwtService.extractUsername(token);
        log.info("Fetching all tasks lists.");
        Page<TasksList> tasksLists = tasksListService.getAllTasksLists(pageable,username);
        log.info("All tasks lists fetched");
        return ResponseEntity.ok(tasksLists);
    }

    @GetMapping("/uncompleted")
    public ResponseEntity<Page<TasksList>> getAllUncompletedTasksLists(
            @RequestHeader("Authorization") String token,
            Pageable pageable) {
        String username = this.jwtService.extractUsername(token);
        log.info("Fetching all uncompleted tasks lists.");
        Page<TasksList> tasksLists = tasksListService.getAllUncompletedTasksLists(pageable,username);
        log.info("All uncompleted tasks lists fetched");
        return ResponseEntity.ok(tasksLists);

    }

    @GetMapping("/completed")
    public ResponseEntity<Page<TasksList>> getAllCompletedTasksLists(
            @RequestHeader("Authorization") String token,
            Pageable pageable) {
        String username = this.jwtService.extractUsername(token);
        log.info("Fetching all completed tasks lists.");
        Page<TasksList> tasksLists = tasksListService.getAllCompletedTasksLists(pageable,username);
        log.info("All completed tasks lists fetched");
        return ResponseEntity.ok(tasksLists);

    }
}
