package me.namila.rx.reactivespringboot.composite.handler;


import me.namila.rx.reactivespringboot.composite.handler.model.APIError;
import me.namila.rx.reactivespringboot.core.exception.MongoException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import reactor.core.publisher.Mono;

import javax.servlet.http.HttpServletRequest;

/**
 * The type Response exception handler.
 */
@ControllerAdvice
public class ResponseExceptionHandler extends ResponseEntityExceptionHandler {


    /**
     * Handle mongo exceptions response entity.
     *
     * @param ex      the ex
     * @param request the request
     * @return the response entity
     */
    @ExceptionHandler(MongoException.class)
    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    public Mono<ResponseEntity<APIError>> handleMongoExceptions(MongoException ex, HttpServletRequest request) {
        return Mono.just(new ResponseEntity<>(new APIError(HttpStatus.INTERNAL_SERVER_ERROR, "MongoDB ERROR OCCURRED", ex, request),
                HttpStatus.NOT_ACCEPTABLE));
    }

}