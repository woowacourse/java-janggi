package janggi.domain.movement;

import java.util.Arrays;

public enum Direction {

    NORTH(-1, 0),
    NORTH_EAST(-1, 1),
    EAST(0, 1),
    SOUTH_EAST(1, 1),
    SOUTH(1, 0),
    SOUTH_WEST(1, -1),
    WEST(0, -1),
    NORTH_WEST(-1, -1);

    private final int row;
    private final int column;

    Direction(final int row, final int column) {
        this.row = row;
        this.column = column;
    }

    public Direction flip() {
        return Direction.pick(-row, -column);
    }

    private static Direction pick(final int row, final int column) {
        return Arrays.stream(values())
            .filter(direction -> direction.row == row && direction.column == column)
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("요청된 행과 열 방향 쌍과 일치하는 방향이 없습니다."));
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }
}
