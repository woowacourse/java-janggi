package janggi.domain;

import java.util.Arrays;
import java.util.List;

public enum Direction {
    NORTH(0, -1),
    SOUTH(0, 1),
    WEST(-1, 0),
    EAST(1, 0),
    NORTH_WEST(-1, -1),
    NORTH_EAST(1, -1),
    SOUTH_WEST(-1, 1),
    SOUTH_EAST(1, 1),
    ;

    private static final List<Direction> STRAIGHT_DIRECTIONS = Arrays.stream(values())
            .filter(Direction::isStraight)
            .toList();
    private static final List<Direction> ALL_DIRECTIONS = Arrays.stream(Direction.values())
            .toList();

    private final int column;
    private final int row;

    Direction(int column, int row) {
        this.column = column;
        this.row = row;
    }

    public Position sumDirection(Position position) {
        return new Position(this.column + position.getColumn(), this.row + position.getRow());
    }

    private boolean isStraight() {
        return (column == 0 || row == 0);
    }

    public static List<Direction> getStraightDirections() {
        return STRAIGHT_DIRECTIONS;
    }

    public static List<Direction> getAllDirections() {
        return ALL_DIRECTIONS;
    }

    public int getColumn() {
        return column;
    }

    public int getRow() {
        return row;
    }
}
