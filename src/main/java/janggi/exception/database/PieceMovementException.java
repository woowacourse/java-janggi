package janggi.exception.database;

public class PieceMovementException extends DatabaseException {
    public PieceMovementException(Throwable cause) {
        super("기물 이동 중 DB 에러가 발생했습니다.", cause);
    }
}
