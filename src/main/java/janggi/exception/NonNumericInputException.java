package janggi.exception;

public class NonNumericInputException extends BusinessException {
    public NonNumericInputException() {
        super("좌표는 숫자만 입력할 수 있습니다.");
    }
}
