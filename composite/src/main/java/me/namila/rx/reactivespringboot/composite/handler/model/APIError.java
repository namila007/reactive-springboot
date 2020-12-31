package me.namila.rx.reactivespringboot.composite.handler.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

/**
 * The type Api error.
 */
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

    /**
     * Instantiates a new Api error.
     */
    public APIError() {
        this.message = "Unexpected error";
        timestamp = LocalDateTime.now();
    }

    /**
     * Instantiates a new Api error.
     *
     * @param status  the status
     * @param request the request
     */
    public APIError(HttpStatus status, HttpServletRequest request) {
        this();
        this.status = status;
        this.path = request.getServletPath();
    }

    /**
     * Instantiates a new Api error.
     *
     * @param status  the status
     * @param ex      the ex
     * @param request the request
     */
    public APIError(HttpStatus status, Throwable ex, HttpServletRequest request) {
        this(status, request);
        this.debugMessage = ex.getLocalizedMessage();
    }

    /**
     * Instantiates a new Api error.
     *
     * @param status  the status
     * @param message the message
     * @param ex      the ex
     * @param request the request
     */
    public APIError(HttpStatus status, String message, Throwable ex, HttpServletRequest request) {
        this(status, ex, request);
        this.message = message;
        this.debugMessage = ex.getMessage();
    }

}