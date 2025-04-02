package janggi.rule;

import janggi.value.Position;

public enum BoardPositionRange {
    X_MAX(8),
    X_MIN(0),
    Y_MAX(9),
    Y_MIN(0);
    private final int value;

    BoardPositionRange(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static void validateInRange(Position position) {
        boolean isXOutOfRange = position.x() < BoardPositionRange.X_MIN.getValue()
                || position.x() > BoardPositionRange.X_MAX.getValue();
        boolean isYOutOfRange = position.y() < BoardPositionRange.Y_MIN.getValue()
                || position.y() > BoardPositionRange.Y_MAX.getValue();
        if (isXOutOfRange || isYOutOfRange) {
            throw new IllegalArgumentException("[ERROR] x좌표는 0~8, y좌표는 0~9 사이로 입력해주세요.");
        }
    }
}
