package me.namila.rx.reactivespringboot.core.exception;

import org.springframework.http.HttpStatus;

/**
 * The type Not found exception.
 */
public class CoreException extends AbstractException {
    /**
     * Instantiates a new Not found exception.
     *
     * @param status the status
     */
    public CoreException(HttpStatus status) {
        super(status);
    }

    /**
     * Instantiates a new Not found exception.
     *
     * @param status the status
     * @param reason the reason
     */
    public CoreException(HttpStatus status, String reason) {
        super(status, reason);
    }

    /**
     * Instantiates a new Not found exception.
     *
     * @param status the status
     * @param reason the reason
     * @param cause  the cause
     */
    public CoreException(HttpStatus status, String reason, Throwable cause) {
        super(status, reason, cause);
    }
}
