package team.janggi.exception;

public class GameNotFinishedException extends RuntimeException {
    public GameNotFinishedException() {
        super("게임이 아직 종료되지 않았습니다.");
    }
    public GameNotFinishedException(String message) {
        super(message);
    }
}
