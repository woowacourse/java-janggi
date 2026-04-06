package janggi.exception.input;

public class InvalidInputSizeException extends InputException {
    public InvalidInputSizeException() {
        super("좌표는 두 개의 숫자(예: 1, 2)로 입력해야 합니다.");
    }
}
