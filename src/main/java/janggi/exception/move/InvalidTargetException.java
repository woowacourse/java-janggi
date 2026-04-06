package janggi.exception.move;

public class InvalidTargetException extends MoveException {
    public InvalidTargetException() {
        super("목적지에 아군 기물이 위치하고 있습니다.");
    }
}
