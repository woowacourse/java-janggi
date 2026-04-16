package janggi.exception;

public class InfrastructureException extends RuntimeException {
    public InfrastructureException(String message) {
        super("[ERROR] " + message);
    }

    public InfrastructureException(String message, Throwable cause) {
        super("[ERROR] " + message, cause);
    }
}
