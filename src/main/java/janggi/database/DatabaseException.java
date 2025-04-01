package janggi.database;

public class DatabaseException extends RuntimeException {

    private static final String ERROR_PREFIX_MESSAGE = "데이터베이스 오류 발생: ";

    public DatabaseException(final String query, final Throwable cause) {
        super(ERROR_PREFIX_MESSAGE + query + cause);
    }
}
