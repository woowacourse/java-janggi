package janggi.exception.database;

public class PieceInitializationException extends DatabaseException {
    public PieceInitializationException(Throwable cause) {
        super("기물 초기 세팅 중 DB 에러가 발생했습니다.", cause);
    }
}
