package janggi.exception.input;

public class InvalidDelimiterException extends InputException {
    public InvalidDelimiterException() {
        super("좌표는 쉼표(,)로 구분되어야 합니다.");
    }
}
