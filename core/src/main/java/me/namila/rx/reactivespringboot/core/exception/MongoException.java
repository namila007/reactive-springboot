package me.namila.rx.reactivespringboot.core.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

/**
 * Mongo exception.
 */
public class MongoException extends ResponseStatusException {

  /**
   * Instantiates a new Mongo exception.
   *
   * @param message the message
   * @param e       the e
   */
  public MongoException(String message, Throwable e) {
    this(HttpStatus.CONFLICT, message, e);
  }

  /**
   * Instantiates a new Mongo exception.
   *
   * @param httpStatus the http status
   * @param message    the message
   * @param e          the e
   *                   <p>Here exception will be thrown by rootcause
   */
  public MongoException(HttpStatus httpStatus, String message, Throwable e) {
    super(httpStatus, message);
  }
}
