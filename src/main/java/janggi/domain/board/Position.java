package janggi.domain.board;

public record Position(int x, int y) {
    public Position {
        validatePositionRange(x, y);
    }

    private void validatePositionRange(int x, int y) {
        validateXRange(x);
        validateYRange(y);
    }

    private void validateXRange(int x) {
        if (BoardRange.MIN_X > x || x > BoardRange.MAX_X) {
            throw new IllegalArgumentException("X 좌표의 범위는 1~9 사이여야 합니다.");
        }
    }

    private void validateYRange(int y) {
        if (BoardRange.MIN_Y > y || y > BoardRange.MAX_Y) {
            throw new IllegalArgumentException("Y 좌표의 범위는 1~10 사이여야 합니다.");
        }
    }

}
