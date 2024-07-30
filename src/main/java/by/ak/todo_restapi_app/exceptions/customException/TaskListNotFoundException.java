package by.ak.todo_restapi_app.exceptions.customException;

public class TaskListNotFoundException extends RuntimeException {

    public TaskListNotFoundException() {
    }

    public TaskListNotFoundException(String message) {
        super(message);
    }

    public TaskListNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public TaskListNotFoundException(Throwable cause) {
        super(cause);
    }
}
