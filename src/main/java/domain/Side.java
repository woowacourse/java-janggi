package domain;

import domain.coordinate.Direction;

public enum Side {

    HAN,
    CHU,
    NEUTRAL;

    public boolean isChu() {
        return this == CHU;
    }

    public boolean isHan() {
        return this == HAN;
    }

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
}
