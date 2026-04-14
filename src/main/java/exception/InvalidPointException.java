package exception;

public class InvalidPointException extends IllegalArgumentException {
    public InvalidPointException() {
        super(ErrorMessage.INVALID_POINT.getMessage());
    }
}
