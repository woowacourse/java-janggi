package domain.board;

import java.util.Arrays;

public enum Column {
    ONE(1, false, false),
    TWO(2, false, false),
    THREE(3, false, false),
    FOUR(4, true, false),
    FIVE(5, true, true),
    SIX(6, true, false),
    SEVEN(7, false, false),
    EIGHT(8, false, false),
    NINE(9, false, false),
    ;

    private final int value;
    private final boolean isPalaceExist;
    private final boolean isPalaceCenterExist;

    Column(int value, boolean isPalaceExist, boolean isPalaceCenterExist) {
        this.value = value;
        this.isPalaceExist = isPalaceExist;
        this.isPalaceCenterExist = isPalaceCenterExist;
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

    public boolean isPalaceCenterExist() {
        return isPalaceCenterExist;
    }
}
