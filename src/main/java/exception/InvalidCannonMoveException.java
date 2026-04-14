package exception;

public class InvalidCannonMoveException extends IllegalArgumentException {
    public InvalidCannonMoveException(ErrorMessage errorMessage) {
        super(errorMessage.getMessage());
    }
}
