package janggi;

public enum Column {

    ZERO(0),
    ONE(1),
    TWO(2),
    THREE(3),
    FOUR(4),
    FIVE(5),
    SIX(6),
    SEVEN(7),
    EIGHT(8);

    private final int value;

    Column(final int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
