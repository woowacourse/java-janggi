package exception;

public class InvalidMoveCoordinateFormatException extends IllegalArgumentException {
    public InvalidMoveCoordinateFormatException(ErrorMessage errorMessage) {
        super(errorMessage.getMessage());
    }
}
