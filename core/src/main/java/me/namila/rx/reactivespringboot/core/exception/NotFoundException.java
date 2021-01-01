package me.namila.rx.reactivespringboot.core.exception;

import org.springframework.http.HttpStatus;

/**
 * The type Not found exception.
 */
public class NotFoundException extends AbstractException {
    /**
     * Instantiates a new Not found exception.
     *
     * @param status the status
     */
    public NotFoundException(HttpStatus status) {
        super(status);
    }

    /**
     * Instantiates a new Not found exception.
     *
     * @param status the status
     * @param reason the reason
     */
    public NotFoundException(HttpStatus status, String reason) {
        super(status, reason);
    }

    /**
     * Instantiates a new Not found exception.
     *
     * @param status the status
     * @param reason the reason
     * @param cause  the cause
     */
    public NotFoundException(HttpStatus status, String reason, Throwable cause) {
        super(status, reason, cause);
    }
}
