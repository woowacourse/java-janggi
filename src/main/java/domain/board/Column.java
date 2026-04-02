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

    public boolean canShift(int delta) {
        int next = this.ordinal() + delta;
        return next >= 0 && next < Column.values().length;
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
