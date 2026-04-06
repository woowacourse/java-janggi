package janggi.exception.move;

public class InvalidMoveException extends MoveException {
    public InvalidMoveException() {
        super("유효하지 않는 행마입니다.");
    }
}
