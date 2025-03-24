package janggi.domain.piece.direction;

import java.util.Objects;

public record Position(int x, int y) {

    private static final int MIN_X = 0;
    private static final int MAX_X = 8;
    private static final int MIN_Y = 0;
    private static final int MAX_Y = 9;

    public Position {
        validatePosition(x, y);
    }

    private void validatePosition(final int x, final int y) {
        if (isInBoard(x, y)) {
            return;
        }
        throw new IllegalArgumentException("보드를 벗어났습니다.");
    }

    public boolean isInBoard(final int x, final int y) {
        return x >= MIN_X && x <= MAX_X && y >= MIN_Y && y <= MAX_Y;
    }

    public boolean canMove(final Direction direction) {
        return isInBoard(x + direction.dx(), y + direction.dy());
    }

    public Position move(final Direction direction) {
        return new Position(x + direction.dx(), y + direction.dy());
    }

    @Override
    public boolean equals(final Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final Position position = (Position) o;
        return x == position.x && y == position.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
