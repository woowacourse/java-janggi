package domain.place.moveStrategy;

import domain.place.piece.Side;

public enum Direction {
    TOP(1, 0),
    DOWN(-1, 0),
    LEFT(0, -1),
    RIGHT(0, 1),
    LEFT_TOP(1, -1),
    RIGHT_TOP(1, 1),
    LEFT_DOWN(-1, -1),
    RIGHT_DOWN(-1, 1);

    private final int row;
    private final int column;

    Direction(int row, int column) {
        this.row = row;
        this.column = column;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public boolean isForward(Side side) {
        if (side == Side.HAN) {
            return this.row == 1;
        }

        return this.row == -1;
    }
}
