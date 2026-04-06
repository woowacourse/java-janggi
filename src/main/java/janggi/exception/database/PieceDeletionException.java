package janggi.exception.database;

public class PieceDeletionException extends DatabaseException {
    public PieceDeletionException(Throwable cause) {
        super("기물 삭제 중 DB 에러가 발생했습니다.", cause);
    }
}
