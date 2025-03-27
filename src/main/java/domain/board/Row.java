package domain.board;

import java.util.Arrays;

public enum Row {
    ONE(1, true),
    TWO(2, true),
    THREE(3, true),
    FOUR(4, false),
    FIVE(5, false),
    SIX(6, false),
    SEVEN(7, false),
    EIGHT(8, true),
    NINE(9, true),
    ZERO(10, true),
    ;

    private final int value;
    private final boolean isPalaceExist;

    Row(int value, boolean isPalaceExist) {
        this.value = value;
        this.isPalaceExist = isPalaceExist;
    }

    public static Row from(int value) {
        return Arrays.stream(Row.values())
                .filter(row -> row.value == value)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("해당 숫자에 맞는 Row가 없습니다."));
    }

    public int getValue() {
        return value;
    }

    public boolean isPalaceExist() {
        return isPalaceExist;
    }
}
