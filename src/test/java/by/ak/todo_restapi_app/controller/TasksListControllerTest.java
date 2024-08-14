package by.ak.todo_restapi_app.controller;

import by.ak.todo_restapi_app.dto.TasksListDTO;
import by.ak.todo_restapi_app.dto.TasksListForUpdatingDTO;
import by.ak.todo_restapi_app.service.TasksListService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TasksListControllerTest {

    @Mock
    TasksListService service;

    @Mock
    private PagedResourcesAssembler<TasksListDTO> pagedResourcesAssembler;

    @InjectMocks
    TasksListController controller;

    @Test
    void deleteTasksList_RequestIsValid_ReturnNoContent(){
        //given
        var id = 1L;
        var username = "user";
        Principal principal = () -> username;
                doNothing().when(this.service).deleteTasksList(id,username);
        //when
        ResponseEntity<Void> response = this.controller.deleteTasksList(principal,id);

        //then
        assertEquals(HttpStatus.NO_CONTENT,response.getStatusCode());
        verify(this.service,times(1)).deleteTasksList(id,username);
        verifyNoMoreInteractions(this.service);
    }

    @Test
    void createTasksList_RequestIsValid_ReturnNewTasksList(){
        //given
        var id = 1L;
        var username = "user";
        var description = "TasksList";
        var dateTime = LocalDateTime.now();
        Principal principal = () -> username;
        TasksListDTO tasksListDTO = TasksListDTO.builder()
                .id(id)
                .description(description)
                .tasks(Collections.emptyList())
                .username(username)
                .isComplete(false)
                .dateTimeOfCreation(dateTime)
                .build();
        when(this.service.createTasksList(description,username)).thenReturn(tasksListDTO);

        //when
        ResponseEntity<TasksListDTO> response = this.controller.createTasksList(principal,description);

        //then
        assertEquals(tasksListDTO,response.getBody());
        verify(this.service).createTasksList(description,username);
        verifyNoMoreInteractions(this.service);
    }

    @Test
    void updateTasksList_RequestIsValid_ReturnUpdatedTasksList(){
        //given
        var id = 1L;
        var username = "user";
        var description = "TasksList";
        var dateTime = LocalDateTime.now();
        Principal principal = () -> username;

        TasksListDTO updatedTasksListDTO = TasksListDTO.builder()
                .id(id)
                .description(description)
                .tasks(Collections.emptyList())
                .username(username)
                .isComplete(true)
                .dateTimeOfCreation(dateTime)
                .build();

        TasksListForUpdatingDTO updates = TasksListForUpdatingDTO.builder()
                .description(description)
                .tasks(Collections.emptyList())
                .dateTimeOfCreation(dateTime)
                .isComplete(true)
                .build();

        when(this.service.updateTasksList(id,updates,username)).thenReturn(updatedTasksListDTO);

        //when
        ResponseEntity<TasksListDTO> response = this.controller.updateTasksList(principal,id,updates);

        //then
        assertEquals(updatedTasksListDTO,response.getBody());
        verify(this.service,times(1)).updateTasksList(id,updates,username);
        verifyNoMoreInteractions(this.service);

    }

    @Test
    void getTaskListById_RequestIsValid_ReturnTaskListById() {
        //given
        var id = 1L;
        var username = "user";
        var description = "TasksList";
        var dateTime = LocalDateTime.now();
        Principal principal = () -> username;

        TasksListDTO tasksListDTO = TasksListDTO.builder()
                .id(id)
                .description(description)
                .tasks(Collections.emptyList())
                .username(username)
                .isComplete(false)
                .dateTimeOfCreation(dateTime)
                .build();

        when(this.service.getTasksListByUsernameAndId(id, username)).thenReturn(tasksListDTO);

        //when
        ResponseEntity<TasksListDTO> response = this.controller.getTaskListById(principal, id);

        //then
        assertEquals(tasksListDTO, response.getBody());
        verify(this.service, times(1)).getTasksListByUsernameAndId(id, username);
        verifyNoMoreInteractions(this.service);
    }

}
