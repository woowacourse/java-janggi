package exception;

public class InvalidMoveDirectionException extends IllegalArgumentException {
    public InvalidMoveDirectionException() {
        super(ErrorMessage.INVALID_MOVE_DIRECTION.getMessage());
    }
}
