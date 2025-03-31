package janggi;

public class GameQuitException extends RuntimeException {

    public GameQuitException() {
        super("게임을 종료합니다");
    }

    public GameQuitException(final String message) {
        super(message);
    }
}
