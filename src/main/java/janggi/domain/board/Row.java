package janggi.domain.board;

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
    NINE(9),
    ZERO(10),
    ;

    private final int value;

    Row(int value) {
        this.value = value;
    }

    public static Row from(int value) {
        if(value == 0) {
            return ZERO;
        }
        return Arrays.stream(Row.values())
                .filter(row -> row.value == value)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("해당 숫자에 맞는 Row가 없습니다."));
    }

    public static boolean isValid(int rowValue) {
        return Arrays.stream(Row.values())
                .anyMatch(row -> row.intValue() == rowValue);
    }

    public int intValue() {
        return value;
    }
}
