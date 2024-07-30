package by.ak.todo_restapi_app.exceptions.customException;

public class TaskServiceException extends RuntimeException {

    public TaskServiceException() {
    }

    public TaskServiceException(String message) {
        super(message);
    }

    public TaskServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public TaskServiceException(Throwable cause) {
        super(cause);
    }
}
