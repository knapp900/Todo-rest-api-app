package by.ak.todo_restapi_app.controller;

import by.ak.todo_restapi_app.dto.TasksListDTO;
import by.ak.todo_restapi_app.service.TasksListService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpEntity;
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
    public ResponseEntity<TasksListDTO> createTasksList(
            Principal principal,
            @RequestBody String description) {
        String username = principal.getName();
        log.info("Creating task list with user: {}.", username);
        TasksListDTO tasksList = tasksListService.createTasksList(description, username);
        log.info("Created task list with user: {}.", username);
        return ResponseEntity.ok(tasksList);
    }


    @PutMapping("/{id}")
    public ResponseEntity<TasksListDTO> updateTasksList(
            Principal principal,
            @PathVariable Long id,
            @RequestBody String description) {

        log.info("Updating task list with id: {}.", id);
        TasksListDTO tasksListDTO = this.tasksListService.updateTasksList(id, description, principal.getName());
        log.info("Task list with id: {} updated.", id);
        return ResponseEntity.ok(tasksListDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TasksListDTO> getTaskListById(
            Principal principal,
            @PathVariable Long id) {

        log.info("Fetching task list by id: {}.", id);
        TasksListDTO tasksLists = tasksListService.getTasksListByUsernameAndId(id, principal.getName());
        log.info("Task list fetched by id: {}", id);

        return ResponseEntity.ok(tasksLists);
    }

    @GetMapping
    public HttpEntity<PagedModel<EntityModel<TasksListDTO>>> getAllTasksLists(
            Principal principal,
            Pageable pageable,
            PagedResourcesAssembler<TasksListDTO> assembler
    ) {
        log.info("Fetching all tasks lists.");
        Page<TasksListDTO> tasksLists = tasksListService.getAllTasksLists(pageable, principal.getName());
        if (tasksLists == null || tasksLists.isEmpty()) {
            log.info("No tasks lists found.");
            return ResponseEntity.noContent().build();
        }
        log.info("All tasks lists fetched");
        return ResponseEntity.ok(assembler.toModel(tasksLists));
    }

    @GetMapping("/uncompleted")
    public HttpEntity<PagedModel<EntityModel<TasksListDTO>>> getAllUncompletedTasksLists(
            Principal principal,
            Pageable pageable,
            PagedResourcesAssembler<TasksListDTO> assembler) {

        log.info("Fetching all uncompleted tasks lists.");
        Page<TasksListDTO> tasksLists = tasksListService.getAllUncompletedTasksLists(pageable, principal.getName());
        if (tasksLists == null || tasksLists.isEmpty()) {
            log.info("No tasks lists found.");
            return ResponseEntity.noContent().build();
        }
        log.info("All uncompleted tasks lists fetched");
        return ResponseEntity.ok(assembler.toModel(tasksLists));

    }

    @GetMapping("/completed")
    public HttpEntity<PagedModel<EntityModel<TasksListDTO>>> getAllCompletedTasksLists(
            Principal principal,
            Pageable pageable,
            PagedResourcesAssembler<TasksListDTO> assembler) {

        log.info("Fetching all completed tasks lists.");
        Page<TasksListDTO> tasksLists = tasksListService.getAllCompletedTasksLists(pageable, principal.getName());
        if (tasksLists == null || tasksLists.isEmpty()) {
            log.info("No tasks lists found.");
            return ResponseEntity.noContent().build();
        }
        log.info("All completed tasks lists fetched");
        return ResponseEntity.ok(assembler.toModel(tasksLists));

    }
}
