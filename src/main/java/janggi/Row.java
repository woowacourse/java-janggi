package janggi;

public enum Row {

    ZERO(0),
    ONE(1),
    TWO(2),
    THREE(3),
    FOUR(4),
    FIVE(5),
    SIX(6),
    SEVEN(7),
    EIGHT(8),
    NINE(9);

    private final int value;

    Row(final int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
