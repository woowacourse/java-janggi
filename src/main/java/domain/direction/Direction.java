package domain.direction;

import domain.position.Position;

public enum Direction {

    NORTH(-1, 0),
    SOUTH(1, 0),
    EAST(0, 1),
    WEST(0, -1),
    NORTH_EAST(-1, 1),
    NORTH_WEST(-1, -1),
    SOUTH_EAST(1, 1),
    SOUTH_WEST(1, -1);

    private final int offsetRow;
    private final int offsetColumn;

    Direction(int offsetRow, int offsetColumn) {
        this.offsetRow = offsetRow;
        this.offsetColumn = offsetColumn;
    }

    public Position move(Position source) {
        return new Position(source.row() + this.offsetRow, source.column() + this.offsetColumn);
    }
}
