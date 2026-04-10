package janggi.exception.input;

public class NonNumericInputException extends InputException {
    public NonNumericInputException() {
        super("좌표는 숫자만 입력할 수 있습니다.");
    }
}
