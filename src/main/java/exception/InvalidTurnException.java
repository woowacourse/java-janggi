package exception;

public class InvalidTurnException extends IllegalArgumentException {
    public InvalidTurnException() {
        super(ErrorMessage.INVALID_TURN.getMessage());
    }
}
