package janggi.value;

public enum PositionRange {
    X_MAX(8),
    X_MIN(0),
    Y_MAX(9),
    Y_MIN(0);
    private final int value;

    PositionRange(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static void validatePositionInRange(Position position) {
        boolean isXOutOfRange = position.x() < PositionRange.X_MIN.getValue()
                || position.x() > PositionRange.X_MAX.getValue();
        boolean isYOutOfRange = position.y() < PositionRange.Y_MIN.getValue()
                || position.y() > PositionRange.Y_MAX.getValue();
        if (isXOutOfRange || isYOutOfRange) {
            throw new IllegalArgumentException("[ERROR] x좌표는 0~8, y좌표는 0~9 사이로 입력해주세요.");
        }
    }
}
