package janggi;

public class GameOverException extends RuntimeException {

    public GameOverException() {
        super("게임을 종료합니다");
    }

    public GameOverException(String message) {
        super(message);
    }
}
