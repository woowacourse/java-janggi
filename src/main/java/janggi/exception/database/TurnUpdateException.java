package janggi.exception.database;

public class TurnUpdateException extends RuntimeException {
    public TurnUpdateException(Throwable cause) {
        super("게임 방 턴 업데이트 중 DB 오류가 발생했습니다.", cause);
    }
}
