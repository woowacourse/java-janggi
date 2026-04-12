package domain.coordinate;

import domain.state.Side;

public enum Direction {

    UP(-1, 0), DOWN(1, 0), LEFT(0, -1), RIGHT(0, 1),
    UP_LEFT(-1, -1), UP_RIGHT(-1, 1), DOWN_LEFT(1, -1), DOWN_RIGHT(1, 1);

    private final int col;
    private final int row;

    Direction(int col, int row) {
        this.col = col;
        this.row = row;
    }

    public static Direction getForward(Side side) {
        if (side == Side.HAN) {
            return Direction.DOWN;
        }

        return Direction.UP;
    }

    public boolean isForward(Direction forward) {
        if (forward == Direction.UP) {
            return this == Direction.UP_LEFT || this == Direction.UP_RIGHT;
        }

        if (forward == Direction.DOWN) {
            return this == Direction.DOWN_LEFT || this == Direction.DOWN_RIGHT;
        }

        return false;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }
}
