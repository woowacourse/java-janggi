package janggi.exception.business;

public class RowOutOfRangeException extends BusinessException {
    public RowOutOfRangeException() {
        super("장기판의 x좌표 범위를 벗어났습니다.");
    }
}
