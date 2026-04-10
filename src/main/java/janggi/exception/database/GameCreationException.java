package janggi.exception.database;

public class GameCreationException extends DatabaseException {
    public GameCreationException(Throwable cause) {
        super("게임 방 생성 중 DB 오류가 발생했습니다.", cause);
    }
}
