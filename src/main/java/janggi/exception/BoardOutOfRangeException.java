package janggi.exception;

public class BoardOutOfRangeException extends BusinessException {
    public BoardOutOfRangeException() {
        super("장기판 범위를 벗어난 좌표입니다.");
    }
}
