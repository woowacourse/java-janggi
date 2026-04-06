package janggi.exception.input;

public class NotNumberException extends InputException {
    public NotNumberException() {
        super("좌표는 숫자로 입력해야 합니다.");
    }
}
