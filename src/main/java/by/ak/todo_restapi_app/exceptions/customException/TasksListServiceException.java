package by.ak.todo_restapi_app.exceptions.customException;

public class TasksListServiceException extends RuntimeException {

    public TasksListServiceException() {
    }

    public TasksListServiceException(String message) {
        super(message);
    }

    public TasksListServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public TasksListServiceException(Throwable cause) {
        super(cause);
    }
}
