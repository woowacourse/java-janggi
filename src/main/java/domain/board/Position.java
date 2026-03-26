package domain.board;

import domain.Direction;

import java.util.Objects;

public class Position {
    private static final int MIN_RANGE = 0;
    private static final int MAX_WIDTH_RANGE = 8;
    private static final int MIN_HEIGHT_RANGE = 9;

    private final int x;
    private final int y;

    public Position(int x, int y) {
        validateRange(x, y);

        this.x = x;
        this.y = y;
    }

    private void validateRange(int x, int y) {
        if (x < MIN_RANGE || x > MAX_WIDTH_RANGE) {
            throw new IllegalArgumentException("좌표 값이 올바르지 않습니다.");
        }

        if (y < MIN_RANGE || y > MIN_HEIGHT_RANGE) {
            throw new IllegalArgumentException("좌표 값이 올바르지 않습니다.");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Position position)) {
            return false;
        }
        return x == position.x && y == position.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Position next(Direction direction) {
        return new Position(x + direction.getDx(), y + direction.getDy());
    }

}
