package janggi;

public enum Row {
    OUT,
    ONE,
    TWO,
    THREE,
    FOUR,
    FIVE,
    SIX,
    SEVEN,
    EIGHT,
    NINE,
    ZERO;

    private static final Row[] CACHE_VALUES = values();

    public Row previous() {
        if (this == OUT) {
            return OUT;
        }

        return CACHE_VALUES[this.ordinal() - 1];
    }


    public Row next() {
        if (this == OUT) {
            return OUT;
        }

        return CACHE_VALUES[(this.ordinal() + 1) % CACHE_VALUES.length];
    }
}
