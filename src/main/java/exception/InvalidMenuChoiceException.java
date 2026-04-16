package exception;

public class InvalidMenuChoiceException extends IllegalArgumentException {
    public InvalidMenuChoiceException() {
        super(ErrorMessage.OUT_OF_RANGE_CHOICE.getMessage());
    }
}
