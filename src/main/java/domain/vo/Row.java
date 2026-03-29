package domain.vo;

import java.util.Arrays;

public enum Row {
    ZERO("0"),
    ONE("1"),
    TWO("2"),
    THREE("3"),
    FOUR("4"),
    FIVE("5"),
    SIX("6"),
    SEVEN("7"),
    EIGHT("8"),
    NINE("9");


    private final String value;

    Row(String value) {
        this.value = value;
    }

    public boolean canShift(int delta) {
        int next = this.ordinal() + delta;
        return next >= 0 && next < Row.values().length;
    }

    public Row shift(int delta) {
        return Row.values()[this.ordinal() + delta];
    }

    public Row reverse() {
        return Row.values()[9 - this.ordinal()];
    }

    public String display() {
        return value;
    }

    public static Row toRow(char character) {
        return Arrays.stream(Row.values())
                .filter(row -> row.value.equals(String.valueOf(character)))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 좌표 형식이 틀렸습니다."));
    }
}
