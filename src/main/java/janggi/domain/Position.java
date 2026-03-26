package janggi.domain;

public class Position {
    private final int x;
    private final int y;

    public Position(int x, int y) {
        validatePositionRange(x, y);
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    private void validatePositionRange(int x, int y) {
        validateXRange(x);
        validateYRange(y);
    }

    private void validateXRange(int x) {
        if (1 > x || x > 9) {
            throw new IllegalArgumentException("X 좌표의 범위는 1~9 사이여야 합니다.");
        }
    }

    private void validateYRange(int y) {
        if (1 > y || y > 10) {
            throw new IllegalArgumentException("Y 좌표의 범위는 1~10 사이여야 합니다.");
        }
    }
}
