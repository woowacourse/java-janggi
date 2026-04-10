package janggi.exception.database;

public class GameLoadException extends DatabaseException {
    public GameLoadException(Throwable cause) {
        super("게임 조회 중 오류 발생", cause);
    }
}
