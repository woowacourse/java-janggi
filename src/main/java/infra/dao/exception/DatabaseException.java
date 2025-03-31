package infra.dao.exception;

public class DatabaseException extends RuntimeException {

    public DatabaseException(
        final String message,
        final Throwable cause
    ) {
        super(message, cause);
    }
}
