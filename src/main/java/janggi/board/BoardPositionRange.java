package janggi.board;

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
}
