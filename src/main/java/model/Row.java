package model;

import java.util.Arrays;

public enum Row {
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

    Row(int value) {
        this.value = value;
    }

    public static Row getRowBy(int inputValue) {
        return Arrays.stream(Row.values())
            .filter(result -> result.value == inputValue)
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 Row입니다."));
    }

}
