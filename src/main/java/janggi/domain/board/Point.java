package janggi.domain.board;

import java.util.Objects;

public record Point(
        int x, int y
) {

    private static final int MAXIMUM_ROW = 10;
    private static final int MINIMUM_ROW = 1;
    private static final int MAXIMUM_COLUMN = 9;
    private static final int MINIMUM_COLUMN = 1;

    public Point {
        validatePoint(x, y);
    }

    private void validatePoint(int x, int y) {
        if (isOutOfBoundary(x, y)) {
            throw new IllegalStateException("보드 내에서 이동해야합니다.");
        }
    }

    private boolean isOutOfBoundary(int x, int y) {
        return x > MAXIMUM_ROW || x < MINIMUM_ROW || y > MAXIMUM_COLUMN || y < MINIMUM_COLUMN;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Point point)) {
            return false;
        }
        return x == point.x && y == point.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
