package domain.board;

import domain.coordinate.Direction;

public enum Side {

    HAN,
    CHU,
    NEUTRAL;

    public boolean isNeutral() {
        return this == NEUTRAL;
    }

    public Side change() {
        if (this == HAN) {
            return CHU;
        }

        return HAN;
    }

    public Direction getForward() {
        if (this == Side.HAN) {
            return Direction.DOWN;
        }

        return Direction.UP;
    }

    public int getStartingRow() {
        if (this == Side.HAN) {
            return 0;
        }

        return 9;
    }
}
