package me.namila.rx.reactivespringboot.core.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public abstract class AbstractException extends ResponseStatusException {
    private final String message;

    public AbstractException(HttpStatus status) {
        super(status);
        message = "Error Occured";
    }

    public AbstractException(HttpStatus status, String reason) {
        super(status, reason);
        message = reason;
    }

    public AbstractException(HttpStatus status, String reason, Throwable cause) {
        super(status, cause.getMessage(), cause);
        message = reason;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
