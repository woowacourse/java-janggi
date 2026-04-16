package exception;

public class InvalidFormationChoiceException extends IllegalArgumentException {
    public InvalidFormationChoiceException() {
        super(ErrorMessage.INVALID_FORMATION_CHOICE.getMessage());
    }
}
