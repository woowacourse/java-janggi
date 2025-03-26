package janggi.piece;

import janggi.position.Position;

public enum Movement {
    UP(-1, 0),
    DOWN(1, 0),
    RIGHT(0, 1),
    LEFT(0, -1),
    RIGHT_UP(RIGHT.row + UP.row, RIGHT.column + UP.column),
    RIGHT_DOWN(RIGHT.row + DOWN.row, RIGHT.column + DOWN.column),
    LEFT_UP(LEFT.row + UP.row, LEFT.column + UP.column),
    LEFT_DOWN(LEFT.row + DOWN.row, LEFT.column + DOWN.column);

    private final int row;
    private final int column;

    Movement(int row, int column) {
        this.row = row;
        this.column = column;
    }

    public Position move(Position position) {
        return position.move(row, column);
    }
}
