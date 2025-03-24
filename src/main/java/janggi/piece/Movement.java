package janggi.piece;

import janggi.position.Position;

public enum Movement {
    UP(-1,0),
    DOWN(1,0),
    RIGHT(0,1),
    LEFT(0,-1),
    RIGHT_UP(RIGHT.row + UP.row, RIGHT.column + UP.column),
    RIGHT_DOWN(RIGHT.row + DOWN.row, RIGHT.column + DOWN.column),
    LEFT_UP(LEFT.row + UP.row, LEFT.column + UP.column),
    LEFT_DOWN(LEFT.row + DOWN.row, LEFT.column + DOWN.column),
    UP_UP_RIGHT(UP.row + RIGHT_UP.row, UP.column + RIGHT_UP.column),
    UP_UP_LEFT(UP.row + LEFT_UP.row, UP.column + LEFT_UP.column),
    DOWN_DOWN_RIGHT(DOWN.row + RIGHT_DOWN.row, DOWN.column + RIGHT_DOWN.column),
    DOWN_DOWN_LEFT(DOWN.row + LEFT_DOWN.row, DOWN.column + LEFT_DOWN.column),
    LEFT_LEFT_UP(LEFT.row + LEFT_UP.row, LEFT.column + LEFT_UP.column),
    LEFT_LEFT_DOWN(LEFT.row + LEFT_DOWN.row, LEFT.column + LEFT_DOWN.column),
    RIGHT_RIGHT_UP(RIGHT.row + RIGHT_UP.row, RIGHT.column + RIGHT_UP.column),
    RIGHT_RIGHT_DOWN(RIGHT.row + RIGHT_DOWN.row , RIGHT.column + RIGHT_DOWN.column);



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
