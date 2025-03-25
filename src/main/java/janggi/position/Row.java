package janggi.position;

import java.util.Arrays;

public enum Row {

    ZERO,
    ONE,
    TWO,
    THREE,
    FOUR,
    FIVE,
    SIX,
    SEVEN,
    EIGHT,
    NINE;

    public Row add(final int dx) {
        return Arrays.stream(values())
                .filter(newValue -> newValue.ordinal() == ordinal() + dx)
                .findAny()
                .orElseThrow(() -> new IllegalStateException("[ERROR] 보드를 벗어난 값입니다."));
    }

    public static Row of(final int value) {
        return Arrays.stream(values())
                .filter(row -> row.ordinal() == value)
                .findAny()
                .orElseThrow(() -> new IllegalStateException("[ERROR] 보드를 벗어난 값입니다."));
    }

    public int getValue() {
        return this.ordinal();
    }

    public boolean isPalace() {
        return ordinal() <= 2 || ordinal() >= 7;
    }
}
