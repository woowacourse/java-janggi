package janggi;

public class AlreadyGameExistsException extends RuntimeException {
    public AlreadyGameExistsException(String message) {
        super(message);
    }
}
