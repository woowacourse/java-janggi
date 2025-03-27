package domain.board;

import java.util.Arrays;

public enum Column {
    ONE(1, false),
    TWO(2, false),
    THREE(3, false),
    FOUR(4, true),
    FIVE(5, true),
    SIX(6, true),
    SEVEN(7, false),
    EIGHT(8, false),
    NINE(9, false),
    ;

    private final int value;
    private final boolean isPalaceExist;

    Column(int value, boolean isPalaceExist) {
        this.value = value;
        this.isPalaceExist = isPalaceExist;
    }

    public static Column from(int value) {
        return Arrays.stream(Column.values())
                .filter(col -> col.value == value)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("해당 숫자에 맞는 Column 이 없습니다."));
    }

    public int getValue() {
        return value;
    }

    public boolean isPalaceExist() {
        return isPalaceExist;
    }
}
