package me.namila.rx.reactivespringboot.core.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

/**
 * The type Abstract exception.
 */
public abstract class AbstractException extends ResponseStatusException {
    private final String message;

    /**
     * Instantiates a new Abstract exception.
     *
     * @param status the status
     */
    public AbstractException(HttpStatus status) {
        super(status);
        message = "Error Occured";
    }

    /**
     * Instantiates a new Abstract exception.
     *
     * @param status the status
     * @param reason the reason
     */
    public AbstractException(HttpStatus status, String reason) {
        super(status, reason);
        message = reason;
    }

    /**
     * Instantiates a new Abstract exception.
     *
     * @param status the status
     * @param reason the reason
     * @param cause  the cause
     */
    public AbstractException(HttpStatus status, String reason, Throwable cause) {
        super(status, cause.getMessage(), cause);
        message = reason;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
