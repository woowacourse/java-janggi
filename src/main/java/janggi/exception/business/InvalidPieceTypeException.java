package janggi.exception.business;

public class InvalidPieceTypeException extends BusinessException {
    public InvalidPieceTypeException() {
        super("존재하지 않는 기물타입입니다.");
    }
}
