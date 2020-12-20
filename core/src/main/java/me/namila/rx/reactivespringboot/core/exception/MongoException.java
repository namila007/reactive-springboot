package me.namila.rx.reactivespringboot.core.exception;

public class MongoException extends RuntimeException {
    public MongoException(String message, Throwable e) {
        super(message, e);
    }
}
