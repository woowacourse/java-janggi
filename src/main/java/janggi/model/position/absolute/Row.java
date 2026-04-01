package janggi.model.position.absolute;

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
    ZERO(10);

    public static final int START = 1;
    public static final int END = 10;

    private  final int value;

    Row(int value) {
        if (value < START || value > END) {
            throw new IllegalArgumentException("value는 1 이상 10 이햐의 자연수여야 합니다.");
        }

        this.value = value;
    }

    public static Row of(int value) {
        return Arrays.stream(values())
                .filter(v -> v.value == value)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 값입니다: " + value));
    }

    public int getDistanceTo(Row other) {
        return this.value - other.value;
    }

    public int getValue() {
        return value;
    }
}
