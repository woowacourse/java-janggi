package janggi.exception;

public class EmptyInputException extends BusinessException {
    public EmptyInputException() {
        super("입력값이 비어있습니다.");
    }
}
