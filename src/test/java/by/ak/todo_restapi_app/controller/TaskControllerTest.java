package by.ak.todo_restapi_app.controller;

import by.ak.todo_restapi_app.dto.TaskDTO;
import by.ak.todo_restapi_app.entity.Importance;
import by.ak.todo_restapi_app.entity.Status;
import by.ak.todo_restapi_app.service.TaskService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.security.Principal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TaskControllerTest {

    @Mock
    TaskService service;

    @InjectMocks
    TaskController controller;

    @Test
    void deleteTask_RequestIsValid_ReturnNoContent() {
        // given
        Long taskId = 1L;
        Long tasksListId = 1L;
        String username = "user";
        Principal principal = () -> username;
        doNothing().when(this.service).deleteTask(tasksListId, taskId, username);

        // when
        ResponseEntity<Void> response = this.controller.deleteTask(tasksListId, taskId, principal);

        // then
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(this.service, times(1)).deleteTask(tasksListId, taskId, username);
        verifyNoMoreInteractions(this.service);
    }

    @Test
    void moveTaskToAnotherTasksList_RequestIsValid_ReturnNoContent() {
        // given
        Long sourceTaskId = 1L;
        Long sourceTasksListId = 1L;
        Long destTasksListId = 2L;
        String username = "user";
        Principal principal = () -> username;
        doNothing().when(this.service).moveTaskToAnotherTasksList(sourceTasksListId, sourceTaskId, destTasksListId, username);

        // when
        ResponseEntity<Void> response = this.controller.moveTaskToAnotherTasksList(sourceTasksListId, sourceTaskId, destTasksListId, principal);

        // then
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(this.service, times(1)).moveTaskToAnotherTasksList(sourceTasksListId, sourceTaskId, destTasksListId, username);
        verifyNoMoreInteractions(this.service);
    }

    @Test
    void createTask_RequestIsValid_ReturnCreatedTask() {
        // given
        Long tasksListId = 1L;
        String username = "user";
        var dataTime = LocalDateTime.now();
        TaskDTO taskDTO = TaskDTO.builder()
                .id(1L)
                .title("Title")
                .content("Content")
                .tasksListId(tasksListId)
                .status(Status.NOT_STARTED)
                .importance(Importance.HIGH)
                .createdDateTime(dataTime)
                .lastModifiedDateTime(dataTime)
                .build();
        TaskDTO createdTaskDTO = TaskDTO.builder()
                .id(1L)
                .title("Title")
                .content("Content")
                .tasksListId(tasksListId)
                .status(Status.NOT_STARTED)
                .importance(Importance.HIGH)
                .createdDateTime(dataTime)
                .lastModifiedDateTime(dataTime)
                .build();  // Ожидаемый результат
        Principal principal = () -> username;
        when(this.service.createTask(tasksListId, taskDTO, username)).thenReturn(createdTaskDTO);

        // when
        ResponseEntity<TaskDTO> response = this.controller.createTask(tasksListId, taskDTO, principal);

        // then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(createdTaskDTO, response.getBody());
        verify(this.service, times(1)).createTask(tasksListId, taskDTO, username);
        verifyNoMoreInteractions(this.service);
    }

    @Test
    void getTask_RequestIsValid_ReturnTask() {
        // given
        Long tasksListId = 1L;
        Long taskId = 1L;
        var dataTime = LocalDateTime.now();
        TaskDTO taskDTO = TaskDTO.builder()
                .id(1L)
                .title("Title")
                .content("Content")
                .tasksListId(tasksListId)
                .status(Status.NOT_STARTED)
                .importance(Importance.HIGH)
                .createdDateTime(dataTime)
                .lastModifiedDateTime(dataTime)
                .build();
        when(this.service.getTask(tasksListId, taskId)).thenReturn(taskDTO);

        // when
        ResponseEntity<TaskDTO> response = this.controller.getTask(tasksListId, taskId);

        // then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(taskDTO, response.getBody());
        verify(this.service, times(1)).getTask(tasksListId, taskId);
        verifyNoMoreInteractions(this.service);
    }

    @Test
    void updateTask_RequestIsValid_ReturnUpdatedTask() {
        // given
        Long tasksListId = 1L;
        Long taskId = 1L;
        var dataTime = LocalDateTime.now();
        TaskDTO taskDTO = TaskDTO.builder()
                .id(1L)
                .title("Title")
                .content("Content")
                .tasksListId(tasksListId)
                .status(Status.NOT_STARTED)
                .importance(Importance.HIGH)
                .createdDateTime(dataTime)
                .lastModifiedDateTime(dataTime)
                .build();
        TaskDTO updatedTaskDTO = TaskDTO.builder()
                .id(1L)
                .title("Title")
                .content("Content")
                .tasksListId(tasksListId)
                .status(Status.COMPLETED)
                .importance(Importance.HIGH)
                .createdDateTime(dataTime)
                .lastModifiedDateTime(dataTime)
                .build();
        when(this.service.updateTask(tasksListId, taskId, taskDTO)).thenReturn(updatedTaskDTO);

        // when
        ResponseEntity<TaskDTO> response = this.controller.updateTask(tasksListId, taskId, taskDTO);

        // then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updatedTaskDTO, response.getBody());
        verify(this.service, times(1)).updateTask(tasksListId, taskId, taskDTO);
        verifyNoMoreInteractions(this.service);
    }

    @Test
    void getAllTasks_RequestIsValid_ReturnTaskPage() {
        // given
        Long tasksListId = 1L;
        Pageable pageable = mock(Pageable.class);
        Page<TaskDTO> taskPage = mock(Page.class);
        when(service.getAllTasks(tasksListId, pageable)).thenReturn(taskPage);

        // when
        ResponseEntity<Page<TaskDTO>> response = this.controller.getAllTasks(tasksListId, pageable);

        // then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(taskPage, response.getBody());
        verify(this.service, times(1)).getAllTasks(tasksListId, pageable);
        verifyNoMoreInteractions(this.service);
    }

    @Test
    void getAllTasksByStatus_RequestIsValid_ReturnTaskPageByStatus() {
        // given
        Long tasksListId = 1L;
        Status status = Status.NOT_STARTED;
        Pageable pageable = mock(Pageable.class);
        Page<TaskDTO> taskPage = mock(Page.class);
        when(this.service.getAllByStatus(status, tasksListId, pageable)).thenReturn(taskPage);

        // when
        ResponseEntity<Page<TaskDTO>> response = this.controller.getAllTasksByStatus(tasksListId, status, pageable);

        // then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(taskPage, response.getBody());
        verify(this.service, times(1)).getAllByStatus(status, tasksListId, pageable);
        verifyNoMoreInteractions(this.service);
    }
}
