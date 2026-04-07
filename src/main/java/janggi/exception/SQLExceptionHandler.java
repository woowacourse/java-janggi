package janggi.exception;

import java.sql.SQLException;

public final class SQLExceptionHandler {

    private SQLExceptionHandler() {

    }

    public static void handle(final SQLException e) throws IllegalStateException {
        final String debugMessage = String.format("Error Code: %s\nError Message: %s\n",
            e.getErrorCode(), e.getMessage());

        throw new IllegalStateException("DB 작업 관련 예외가 발생했습니다.\n" + debugMessage, e);
    }

    public static void handle(final SQLException e, final String userMessage) throws IllegalStateException {
        final String debugMessage = String.format("Error Code: %s\nError Message: %s\n",
            e.getErrorCode(), e.getMessage());

        throw new IllegalStateException(userMessage + "\n" + debugMessage, e);
    }

}
