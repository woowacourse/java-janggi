package janggi;

import java.util.Arrays;

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

    public Column add(final int dx) {
        return Arrays.stream(values())
                .filter(newValue -> newValue.getValue() == value + dx)
                .findAny()
                .orElseThrow(() -> new IllegalStateException("[ERROR] 보드를 벗어난 값입니다."));
    }

    public static Column of(final int value) {
        return Arrays.stream(values())
                .filter(column -> column.getValue() == value)
                .findAny()
                .orElseThrow(() -> new IllegalStateException("[ERROR] 보드를 벗어난 값입니다."));
    }
}
