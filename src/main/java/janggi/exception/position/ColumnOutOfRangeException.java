package janggi.exception.position;

public class ColumnOutOfRangeException extends PositionException {
    public ColumnOutOfRangeException() {
        super("장기판의 y좌표 범위를 벗어났습니다.");
    }
}
