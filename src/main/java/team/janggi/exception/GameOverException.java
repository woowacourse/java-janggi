package team.janggi.exception;

public class GameOverException extends RuntimeException {
    public GameOverException() {
        super("게임이 종료되어 더 이상 진행할 수 없습니다.");
    }

    public GameOverException(String message) {
        super(message);
    }
}
