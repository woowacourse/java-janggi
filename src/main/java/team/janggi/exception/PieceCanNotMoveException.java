package team.janggi.exception;

public class PieceCanNotMoveException extends RuntimeException {
    public PieceCanNotMoveException(String message) {
        super(message);
    }
}
