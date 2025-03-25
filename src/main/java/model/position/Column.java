package model.position;

import java.util.Arrays;

public enum Column {
    ONE(1),
    TWO(2),
    THREE(3),
    FOUR(4),
    FIVE(5),
    SIX(6),
    SEVEN(7),
    EIGHT(8),
    NINE(9),
    TEN(10);

    private static final int MIN_VALUE = 1;
    private static final int MAX_VALUE = 10;

    private final int value;

    Column(int value) {
        this.value = value;
    }

    public static Column getColumnBy(int inputValue) {
        return Arrays.stream(Column.values())
            .filter(result -> result.value == inputValue)
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 Column입니다."));
    }

    public Column up() {
        return getColumnBy(this.value + 1);
    }

    public Column up(int increaseAmount) {
        return getColumnBy(this.value + increaseAmount);
    }

    public boolean canUp() {
        return this.value < MAX_VALUE;
    }

    public boolean canUp(int increaseAmount) {
        return this.value + increaseAmount <= MAX_VALUE;
    }

    public Column down() {
        return getColumnBy(this.value - 1);
    }

    public Column down(int decreaseAmount) {
        return getColumnBy(this.value - Math.abs(decreaseAmount));
    }

    public boolean canDown() {
        return this.value > MIN_VALUE;
    }

    public boolean canDown(int decreaseAmount) {
        return this.value - Math.abs(decreaseAmount) >= MIN_VALUE;
    }

    public int getValue() {
        return value;
    }
}
