package janggi.exception;

public class DatabaseConnectionException extends RuntimeException {

    private static final String MESSAGE = "데이터베이스에 연결 중 문제가 발생하였습니다.";

    public DatabaseConnectionException() {
        super(MESSAGE);
    }
}
