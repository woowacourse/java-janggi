package janggi.domain.piece.direction;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public record Position(int x, int y) {

    private static final int MIN_X = 0;
    private static final int MAX_X = 8;
    private static final int MIN_Y = 0;
    private static final int MAX_Y = 9;

    private static final int PALACE_MIN_X = 3;
    private static final int PALACE_MAX_X = 5;
    private static final int BLUE_PALACE_MIN_Y = 0;
    private static final int BLUE_PALACE_MAX_Y = 2;
    private static final int RED_PALACE_MIN_Y = 7;
    private static final int RED_PALACE_MAX_Y = 9;

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

    public boolean isInPalace() {
        return isInBluePalace() || isInRedPalace();
    }

    private boolean isInBluePalace() {
        return x >= PALACE_MIN_X && x <= PALACE_MAX_X && y >= BLUE_PALACE_MIN_Y && y <= BLUE_PALACE_MAX_Y;
    }

    private boolean isInRedPalace() {
        return x >= PALACE_MIN_X && x <= PALACE_MAX_X && y >= RED_PALACE_MIN_Y && y <= RED_PALACE_MAX_Y;
    }

    public boolean canMoveDiagonalPosition() {
        Set<Position> positions = new HashSet<>();
        positions.add(new Position(3, 2));
        positions.add(new Position(5, 2));
        positions.add(new Position(3, 0));
        positions.add(new Position(5, 0));
        positions.add(new Position(4, 1));

        positions.add(new Position(3, 7));
        positions.add(new Position(5, 7));
        positions.add(new Position(3, 9));
        positions.add(new Position(5, 9));
        positions.add(new Position(4, 8));

        return positions.contains(this);
    }

    public boolean canMove(final Direction direction) {
        final int newX = x + direction.dx();
        final int newY = y + direction.dy();
        return isInBoard(newX, newY);
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
