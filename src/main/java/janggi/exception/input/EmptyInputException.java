package janggi.exception.input;

public class EmptyInputException extends InputException {
    public EmptyInputException() {
        super("입력값이 비어있습니다.");
    }
}
