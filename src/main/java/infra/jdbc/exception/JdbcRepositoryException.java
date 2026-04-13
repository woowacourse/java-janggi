package infra.jdbc.exception;

public class JdbcRepositoryException extends RuntimeException {

    public JdbcRepositoryException(String message, Throwable cause) {
        super(message, cause);
    }

    public JdbcRepositoryException(String message) {
        super(message);
    }
}
