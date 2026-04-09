package janggi.exception.position;

public class RowOutOfRangeException extends PositionException {
    public RowOutOfRangeException() {
        super("장기판의 x좌표 범위를 벗어났습니다.");
    }
}
