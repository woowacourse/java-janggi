package janggi.exception.position;

public class InvalidPositionException extends PositionException {
    public InvalidPositionException() {
        super("존재하지 않는 좌표입니다.");
    }
}
