package janggi.domain.piece;

public enum HorseSide {
    LEFT(0),
    RIGHT(1);

    private final int value;

    HorseSide(final int value) {
        this.value = value;
    }

    public int value() {
        return value;
    }
}
