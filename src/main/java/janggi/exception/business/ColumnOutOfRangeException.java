package janggi.exception.business;

public class ColumnOutOfRangeException extends BusinessException {
    public ColumnOutOfRangeException() {
        super("장기판의 y좌표 범위를 벗어났습니다.");
    }
}
