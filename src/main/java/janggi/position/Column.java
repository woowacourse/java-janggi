package janggi.position;

import java.util.Arrays;

public enum Column {

    ZERO,
    ONE,
    TWO,
    THREE,
    FOUR,
    FIVE,
    SIX,
    SEVEN,
    EIGHT;

    public Column add(final int dx) {
        return Arrays.stream(values())
                .filter(newValue -> newValue.ordinal() == ordinal() + dx)
                .findAny()
                .orElseThrow(() -> new IllegalStateException("[ERROR] 보드를 벗어난 값입니다."));
    }

    public static Column of(final int value) {
        return Arrays.stream(values())
                .filter(column -> column.ordinal() == value)
                .findAny()
                .orElseThrow(() -> new IllegalStateException("[ERROR] 보드를 벗어난 값입니다."));
    }
}
