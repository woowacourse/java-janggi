package domain;

import java.util.List;
import java.util.Objects;
import java.util.Set;

import domain.enums.Direction;

public class Position {
    public static final int MAX_ROW = 10;
    public static final int MAX_COL = 9;
    public static final int MIN_ROW_COL = 1;
    private static final Set<Position> PALACE_DIAGONAL_POSITIONS = Set.of(
            Position.create(1, 4), Position.create(1, 6), Position.create(2, 5),
            Position.create(3, 4), Position.create(3, 6), Position.create(8, 4),
            Position.create(8, 6), Position.create(9, 5), Position.create(10, 4),
            Position.create(10, 6)
    );

    private final int x;
    private final int y;

    private Position(int x, int y) {
        validateBoardSize(x, y);
        this.x = x;
        this.y = y;
    }

    public static Position create(int x, int y) {
        return new Position(x, y);
    }

    private void validateBoardSize(int x, int y) {
        if (x < MIN_ROW_COL || x > MAX_ROW || y < MIN_ROW_COL || y > MAX_COL) {
            throw new IllegalArgumentException("좌표 범위를 벗어났습니다.");
        }
    }

    public boolean isInPalace() {
        int x = getX();
        int y = getY();
        return (y >= 4 && y <= 6) && ((x >= 1 && x <= 3) || (x >= 8 && x <= 10));
    }

    public boolean isPalaceDiagonal() {
        return PALACE_DIAGONAL_POSITIONS.contains(this);
    }

    public void addPalaceDirection(List<Direction> directions) {
        if (isPalaceDiagonal()){
            directions.addAll(Direction.getDiagonalDirections());
        }
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Position position = (Position) o;
        return x == position.x && y == position.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
