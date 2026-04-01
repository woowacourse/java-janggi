package janggi.model.board.position;

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
    NINE(9);

    private final int value;

    Column(int value) {
        if (value < 1 || value > 9) {
            throw new IllegalArgumentException("value는 1 이상 9 이햐의 자연수여야 합니다.");
        }

        this.value = value;
    }

    public static Column of(int value) {
        return Arrays.stream(values())
                .filter(v -> v.value == value)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 값입니다."));
    }

    public int getDistanceTo(Column other) {
        return this.ordinal() - other.ordinal();
    }

    public int getValue() {
        return value;
    }
}
