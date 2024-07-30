package by.ak.todo_restapi_app.controller;

import by.ak.todo_restapi_app.entity.TasksList;
import by.ak.todo_restapi_app.service.TasksListService;
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
@RequestMapping("/api/v1/auth/lists")
public class TasksListController {

    private final TasksListService tasksListService;

    @DeleteMapping("/{id}")
    ResponseEntity<Void> deleteTasksList(
            Principal principal,
            @PathVariable Long id) {

        log.info("Deleting task list with id: {}.", id);
        this.tasksListService.deleteTasksList(id, principal.getName());
        log.info("Task list with id: {} deleted.", id);
        return ResponseEntity.noContent().build();
    }


    @PostMapping
    public ResponseEntity<TasksList> createTasksList(
            Principal principal,
            @RequestBody String description) {
        String username = principal.getName();
        log.info("Creating task list with user: {}.", username);
        TasksList tasksList = tasksListService.createTasksList(description,username);
        log.info("Created task list with user: {}.", username);
        return ResponseEntity.ok(tasksList);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TasksList> updateTasksList(
            Principal principal,
            @PathVariable Long id,
            @RequestBody String description) {

        log.info("Updating task list with id: {}.", id);
        this.tasksListService.updateTasksList(id, description,principal.getName());
        log.info("Task list with id: {} updated.", id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<TasksList> getTaskListById(
            Principal principal,
            @PathVariable Long id){

        log.info("Fetching task list by id: {}.",id);
        TasksList tasksLists = tasksListService.getTasksListByUsernameAndId(id,principal.getName());
        log.info("Task list fetched by id: {}",id);

        return ResponseEntity.ok(tasksLists);
    }

    @GetMapping
    public ResponseEntity<Page<TasksList>> getAllTasksLists(
            Principal principal,
            Pageable pageable) {

        log.info("Fetching all tasks lists.");
        Page<TasksList> tasksLists = tasksListService.getAllTasksLists(pageable, principal.getName());
        log.info("All tasks lists fetched");
        return ResponseEntity.ok(tasksLists);
    }

    @GetMapping("/uncompleted")
    public ResponseEntity<Page<TasksList>> getAllUncompletedTasksLists(
            Principal principal,
            Pageable pageable) {

        log.info("Fetching all uncompleted tasks lists.");
        Page<TasksList> tasksLists = tasksListService.getAllUncompletedTasksLists(pageable, principal.getName());
        log.info("All uncompleted tasks lists fetched");
        return ResponseEntity.ok(tasksLists);

    }

    @GetMapping("/completed")
    public ResponseEntity<Page<TasksList>> getAllCompletedTasksLists(
            Principal principal,
            Pageable pageable) {

        log.info("Fetching all completed tasks lists.");
        Page<TasksList> tasksLists = tasksListService.getAllCompletedTasksLists(pageable,principal.getName());
        log.info("All completed tasks lists fetched");
        return ResponseEntity.ok(tasksLists);

    }
}
