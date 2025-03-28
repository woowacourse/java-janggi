package janggi.setting;

import janggi.value.Position;

public enum BoardCoordinate {
    X_MAX(8),
    X_MIN(0),
    Y_MAX(9),
    Y_MIN(0);
    private final int value;

    BoardCoordinate(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static void validateInRange(Position position) {
        boolean isXOutOfRange = position.x() < BoardCoordinate.X_MIN.getValue()
                || position.x() > BoardCoordinate.X_MAX.getValue();
        boolean isYOutOfRange = position.y() < BoardCoordinate.Y_MIN.getValue()
                || position.y() > BoardCoordinate.Y_MAX.getValue();
        if (isXOutOfRange || isYOutOfRange) {
            throw new IllegalArgumentException("[ERROR] x좌표는 0~8, y좌표는 0~9 사이로 입력해주세요.");
        }
    }
}
