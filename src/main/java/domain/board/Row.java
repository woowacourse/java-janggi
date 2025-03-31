package domain.board;

import java.util.Arrays;

public enum Row {

    ONE(1, true, false),
    TWO(2, true, true),
    THREE(3, true, false),
    FOUR(4, false, false),
    FIVE(5, false, false),
    SIX(6, false, false),
    SEVEN(7, false, false),
    EIGHT(8, true, false),
    NINE(9, true, true),
    ZERO(10, true, false),
    ;

    private final int value;
    private final boolean isPalaceExist;
    private final boolean isPalaceCenterExist;

    Row(int value, boolean isPalaceExist, boolean isPalaceCenterExist) {
        this.value = value;
        this.isPalaceExist = isPalaceExist;
        this.isPalaceCenterExist = isPalaceCenterExist;
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

    public boolean isPalaceCenterExist() {
        return isPalaceCenterExist;
    }
}
