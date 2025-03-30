package janggi.exception;

public class DataAccessException extends RuntimeException {

    private static final String ERROR_PREFIX = "[DATA ACCESS ERROR] ";

    public DataAccessException(String message, Throwable cause) {
        super(ERROR_PREFIX + message, cause);
    }

    public DataAccessException(String message) {
        super(ERROR_PREFIX + message);
    }
}
