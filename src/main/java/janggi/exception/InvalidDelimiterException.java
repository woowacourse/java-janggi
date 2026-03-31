package janggi.exception;

public class InvalidDelimiterException extends BusinessException {
    public InvalidDelimiterException() {
        super("좌표는 쉼표(,)로 구분되어야 합니다.");
    }
}
