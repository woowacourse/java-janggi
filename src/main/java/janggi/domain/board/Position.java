package janggi.domain.board;

import java.util.Objects;

public record Position(
        int x, int y
) {
    private static final int MAXIMUM_ROW = 10;
    private static final int MINIMUM_ROW = 1;
    private static final int MAXIMUM_COLUMN = 9;
    private static final int MINIMUM_COLUMN = 1;

    public Position {
        validatePoint(x, y);
    }

    public Position move(Direction direction) {
        return switch (direction) {
            case UP -> new Position(x - 1, y);
            case DOWN -> new Position(x + 1, y);
            case LEFT -> new Position(x, y - 1);
            case RIGHT -> new Position(x, y + 1);
            case UP_LEFT_DIAGONAL -> new Position(x - 1, y - 1);
            case UP_RIGHT_DIAGONAL -> new Position(x - 1, y + 1);
            case DOWN_LEFT_DIAGONAL -> new Position(x + 1, y - 1);
            case DOWN_RIGHT_DIAGONAL -> new Position(x + 1, y + 1);
        };
    }

    public boolean canMove(Direction direction) {
        try {
            move(direction);
        } catch (IllegalArgumentException ex) {
            return false;
        }
        return true;
    }

    private void validatePoint(int x, int y) {
        if (isOutOfBoundary(x, y)) {
            throw new IllegalArgumentException("존재하지 않는 좌표입니다.");
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
        if (!(o instanceof Position point)) {
            return false;
        }
        return x == point.x && y == point.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
