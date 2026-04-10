package janggi.exception.input;

public class InvalidMenuNumberException extends InputException {
    public InvalidMenuNumberException() {
        super("1, 2, 3 중에서만 선택할 수 있습니다. 다시 입력해주세요.");
    }
}
