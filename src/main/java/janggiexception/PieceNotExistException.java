package janggiexception;

public class PieceNotExistException extends RuntimeException {

    public PieceNotExistException() {
        super("움직일 기물이 존재하지 않습니다.");
    }
}
