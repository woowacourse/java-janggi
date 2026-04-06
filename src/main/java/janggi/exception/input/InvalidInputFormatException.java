package janggi.exception.input;

public class InvalidInputFormatException extends InputException {
    public InvalidInputFormatException() {
        super("쉼표(,)를 기준으로 입력하세요.");
    }
}
