package janggi;

public enum Column {
    OUT,
    ONE,
    TWO,
    THREE,
    FOUR,
    FIVE,
    SIX,
    SEVEN,
    EIGHT,
    NINE;

    private static final Column[] CACHE_VALUES = values();


    public Column next() {
        if (this == OUT) {
            return OUT;
        }
        return CACHE_VALUES[(this.ordinal() + 1) % CACHE_VALUES.length];
    }


    public Column previous() {
        if (this == OUT) {
            return OUT;
        }
        return CACHE_VALUES[(this.ordinal() - 1)];
    }
}
