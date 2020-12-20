package me.namila.rx.reactivespringboot.composite.handler.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class APIError {
    //ToDo modify this to handle error codes and errors ie:hashmaps
    private HttpStatus status;

    @JsonFormat(shape = JsonFormat.Shape.STRING,
            pattern = "yyyy-MM-dd hh:mm:ss")
    private LocalDateTime timestamp;

    private String message;
    private String debugMessage;
    private String path;

    public APIError() {
        this.message = "Unexpected error";
        timestamp = LocalDateTime.now();
    }

    public APIError(HttpStatus status, WebRequest request) {
        this();
        this.status = status;
        this.path = request.getContextPath();
    }

    public APIError(HttpStatus status, Throwable ex, WebRequest request) {
        this(status, request);
        this.debugMessage = ex.getLocalizedMessage();
    }

    public APIError(HttpStatus status, String message, Throwable ex, WebRequest request) {
        this(status, ex, request);
        this.message = message;
        this.debugMessage = ex.getMessage();
    }

}