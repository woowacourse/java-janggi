package janggi.setting;

import janggi.value.Position;

public enum GungSungCoordinate {
    X_MAX(8),
    X_MIN(0),
    Y_MAX(9),
    Y_MIN(0);
    private final int value;

    GungSungCoordinate(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static boolean isInRange(Position position) {
        boolean isXInOfRange = position.x() >= GungSungCoordinate.X_MIN.getValue() ||
                position.x() <= GungSungCoordinate.X_MAX.getValue();
        boolean isYInOfRange = position.y() >= GungSungCoordinate.Y_MIN.getValue() ||
                position.y() <= GungSungCoordinate.Y_MAX.getValue();
        return isXInOfRange && isYInOfRange;
    }
}
