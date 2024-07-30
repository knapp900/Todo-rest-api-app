package by.ak.todo_restapi_app.exceptions;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;
import java.util.Map;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ErrorResponse {
    private ZonedDateTime timestamp;
    private int status;
    private String message;
    private Map<String, String> errors;
    private String method;
    private String path;

}