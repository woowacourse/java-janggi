package model.position;

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
    NINE(9);

    private static final int MIN_VALUE = 1;
    private static final int MAX_VALUE = 9;

    private final int value;

    Row(int value) {
        this.value = value;
    }

    public static Row findRowFrom(int inputValue) {
        return Arrays.stream(Row.values())
            .filter(result -> result.value == inputValue)
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 Row입니다."));
    }

    public Row down() {
        return findRowFrom(this.value - 1);
    }

    public Row down(int decreaseAmount) {
        return findRowFrom(this.value - Math.abs(decreaseAmount));
    }

    public boolean canDown(int decreaseAmount) {
        return this.value - Math.abs(decreaseAmount) >= MIN_VALUE;
    }

    public Row up() {
        return findRowFrom(this.value + 1);
    }

    public Row up(int increaseAmount) {
        return findRowFrom(this.value + increaseAmount);
    }

    public boolean canUp(int increaseAmount) {
        return this.value + increaseAmount <= MAX_VALUE;
    }

    public static Row createFrom(String input) {
        return Row.valueOf(input);
    }
}
