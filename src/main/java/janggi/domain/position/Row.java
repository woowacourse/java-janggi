package janggi.domain.position;

import java.util.Arrays;

public enum Row {

    ZERO(0),
    ONE(1),
    TWO(2),
    THREE(3),
    FOUR(4),
    FIVE(5),
    SIX(6),
    SEVEN(7),
    EIGHT(8),
    NINE(9);

    private static final int MAXIMUM = 9;
    private static final int MINIMUM = 0;

    private final int value;

    Row(final int value) {
        this.value = value;
    }

    public static Row of(final int value) {
        return Arrays.stream(values())
                .filter(column -> column.getValue() == value)
                .findAny()
                .orElseThrow(() -> new IllegalStateException("[ERROR] 보드를 벗어난 값입니다."));
    }

    public Row move(final int movement) {
        return Arrays.stream(Row.values())
                .filter(row -> this.value + movement == row.value)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 보드를 벗어나는 위치입니다."));
    }

    public boolean canMove(int movement) {
        int movedColumn = value + movement;
        return movedColumn >= MINIMUM && movedColumn <= MAXIMUM;
    }

    public int difference(Row other) {
        return this.value - other.value;
    }

    public int getValue() {
        return value;
    }
}
