package janggi.exception;

public class PersistenceTimeoutException extends RuntimeException {

    public PersistenceTimeoutException(String message, Throwable cause) {
        super(message, cause);
    }
}
