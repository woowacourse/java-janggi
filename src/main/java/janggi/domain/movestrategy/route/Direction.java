package janggi.domain.movestrategy.route;

import janggi.domain.position.Column;
import janggi.domain.position.Position;
import janggi.domain.position.Row;

public enum Direction {
    NORTH(-1, 0),
    SOUTH(1, 0),
    WEST(0, -1),
    EAST(0, 1),
    NORTH_WEST(-1, -1),
    NORTH_EAST(-1, 1),
    SOUTH_WEST(1, -1),
    SOUTH_EAST(1, 1);

    private final int row;
    private final int column;

    Direction(int row, int column) {
        this.row = row;
        this.column = column;
    }

    public Position move(Position current) {
        return Position.of(
                Row.of(current.getRow() + row),
                Column.of(current.getColumn() + column)
        );
    }
}
