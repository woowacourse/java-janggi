package janggi.exception.input;

public class InvalidGameIdException extends InputException {
    public InvalidGameIdException() {
        super("ID는 1 이상의 숫자만 입력 가능합니다. 다시 시도해주세요.");
    }
}
