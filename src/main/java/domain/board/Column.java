package domain.board;

import java.util.Arrays;

public enum Column {
    A,
    B,
    C,
    D,
    E,
    F,
    G,
    H,
    I;

    public static final int MIN_INDEX = 0;
    public static final int MAX_INDEX = 8;

    public boolean canShift(int delta) {
        int next = this.ordinal() + delta;
        return MIN_INDEX <= next && next <= MAX_INDEX;
    }

    public Column shift(int delta) {
        return Column.values()[this.ordinal() + delta];
    }

    public static Column toColumn(char character) {
        char upperChar = Character.toUpperCase(character);

        return Arrays.stream(values())
                .filter(column -> column.name().charAt(0) == upperChar)
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 올바른 열 값이 아닙니다."));
    }
}
