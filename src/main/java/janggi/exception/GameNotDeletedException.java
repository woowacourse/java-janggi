package janggi.exception;

public class GameNotDeletedException extends IllegalStateException {
    public GameNotDeletedException() {
        super("게임 데이터가 삭제되지 않았습니다.");
    }
}
