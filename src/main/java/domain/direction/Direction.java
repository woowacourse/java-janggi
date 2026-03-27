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
    private final int offsetCol;

    Direction(int offsetRow, int offsetCol) {
        this.offsetRow = offsetRow;
        this.offsetCol = offsetCol;
    }

    public Position move(Position src) {
        return new Position(src.row() + this.offsetRow, src.col() + this.offsetCol);
    }
}
