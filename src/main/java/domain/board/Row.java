package domain.board;

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
    TEN(10),
    ;

    Row(int value) {
        this.value = value;
    }

    public static Row from(int value) {
        return Arrays.stream(Row.values())
                .filter(row -> row.value == value)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("해당 숫자에 맞는 Row가 없습니다."));
    }

    private final int value;

    public int getValue() {
        return value;
    }
}
