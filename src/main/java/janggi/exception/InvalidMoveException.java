package janggi.exception;

public class InvalidMoveException extends BusinessException {
    public InvalidMoveException() {
        super("유효하지 않는 행마입니다.");
    }
}
