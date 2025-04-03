package janggi.exception;

public class DatabaseQueryException extends RuntimeException {

    private static final String MESSAGE = "데이터베이스에 쿼리 중 문제가 발생하였습니다.";

    public DatabaseQueryException() {
        super(MESSAGE);
    }
}
