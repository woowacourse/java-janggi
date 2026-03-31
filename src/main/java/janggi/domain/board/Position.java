package janggi.domain.board;

import java.util.Objects;

public class Position {
    private static final int MIN_X = 1;
    private static final int MAX_X = 9;
    private static final int MIN_Y = 1;
    private static final int MAX_Y = 10;

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
        if (MIN_X > x || x > MAX_X) {
            throw new IllegalArgumentException("X 좌표의 범위는 1~9 사이여야 합니다.");
        }
    }

    private void validateYRange(int y) {
        if (MIN_Y > y || y > MAX_Y) {
            throw new IllegalArgumentException("Y 좌표의 범위는 1~10 사이여야 합니다.");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Position position = (Position) o;
        return x == position.x && y == position.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
