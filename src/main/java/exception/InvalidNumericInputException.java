package exception;

public class InvalidNumericInputException extends IllegalArgumentException {
    public InvalidNumericInputException() {
        super(ErrorMessage.INVALID_NUMERIC_INPUT.getMessage());
    }
}
