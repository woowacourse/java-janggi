package domain.piece.error;

public class InvalidMovementException extends PieceException {

    public InvalidMovementException(String message) {
        super(message);
    }
}
