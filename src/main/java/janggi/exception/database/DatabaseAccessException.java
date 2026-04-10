package janggi.exception.database;

public class DatabaseAccessException extends DatabaseException {
    public DatabaseAccessException(Throwable cause) {
        super("DB 서버와 연결할 수 없습니다.", cause);
    }
}
