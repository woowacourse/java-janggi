package janggi.exception.database;

public class GameUpdateException extends DatabaseException {
    public GameUpdateException(Throwable cause) {
        super("장기 게임(턴+기물) 갱신 중 오류가 발생했습니다.", cause);
    }
}
