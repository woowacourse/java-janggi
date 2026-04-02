package janggi.exception.business;

public class EmptyPieceException extends BusinessException {
    public EmptyPieceException() {
        super("기물이 존재하지 않습니다.");
    }
}
