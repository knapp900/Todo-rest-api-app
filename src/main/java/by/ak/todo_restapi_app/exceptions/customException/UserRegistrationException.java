package by.ak.todo_restapi_app.exceptions.customException;

public class UserRegistrationException extends RuntimeException{
    public UserRegistrationException() {
    }

    public UserRegistrationException(String message) {
        super(message);
    }

    public UserRegistrationException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserRegistrationException(Throwable cause) {
        super(cause);
    }
}
