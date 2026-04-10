package janggi.exception;


public class DatabaseException extends JanggiException{

    public DatabaseException(ErrorCode errorCode, Throwable cause) {
        super(errorCode.getMessage(), cause);
    }

    public DatabaseException(String message, Throwable cause) {
        super(message, cause);
    }

    public DatabaseException(String message) {
        super(message);
    }
}
