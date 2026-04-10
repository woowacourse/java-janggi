package db.util;

public class DataAccessException extends RuntimeException {

    public DataAccessException(String message) {
        super(message);
    }

    public DataAccessException(Exception exception) {
        super(exception);
    }
}
