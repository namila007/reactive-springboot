package me.namila.rx.reactivespringboot.composite.handler;


import me.namila.rx.reactivespringboot.composite.handler.model.APIError;
import me.namila.rx.reactivespringboot.core.exception.MongoException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ResponseExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(MongoException.class)
    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    public ResponseEntity<APIError> handleMongoExceptions(MongoException ex, WebRequest request) {
        return new ResponseEntity<>(new APIError(HttpStatus.INTERNAL_SERVER_ERROR, "MongoDB ERROR OCCURRED", ex, request),
                HttpStatus.NOT_ACCEPTABLE);
    }

}