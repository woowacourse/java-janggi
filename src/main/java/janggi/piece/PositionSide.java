package janggi.piece;

public enum PositionSide {
    LEFT(0),
    RIGHT(1);
    private final int value;

    PositionSide(final int value) {
        this.value = value;
    }

    public int value() {
        return value;
    }
}