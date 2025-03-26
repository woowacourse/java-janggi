package janggi.board.position;

import janggi.board.PositionOutOfBoardBoundsException;
import java.util.Arrays;

public enum Row {
    NINE(9),
    EIGHT(8),
    SEVEN(7),
    SIX(6),
    FIVE(5),
    FOUR(4),
    THREE(3),
    TWO(2),
    ONE(1),
    ZERO(0);

    public static final int SIZE = 10;

    private final int value;

    Row(final int value) {
        validate(value);
        this.value = value;
    }

    public static Row from(int value) {
        return Arrays.stream(values())
                .filter(row -> row.value == value)
                .findAny()
                .orElseThrow(() -> new PositionOutOfBoardBoundsException("[ERROR] 올바르지 않은 행입니다."));
    }

    private void validate(int value) {
        if (value < 0 || value >= SIZE) {
            throw new PositionOutOfBoardBoundsException("[ERROR] 올바르지 않은 행입니다.");
        }
    }

    public boolean canUp() {
        return value < SIZE - 1;
    }

    public boolean canDown() {
        return value > 0;
    }

    public Row up() {
        if (canUp()) {
            return from(value + 1);
        }
        throw new PositionOutOfBoardBoundsException("[ERROR] 더 이상 행을 증가할 수 없습니다.");
    }

    public Row down() {
        if (canDown()) {
            return from(value - 1);
        }
        throw new PositionOutOfBoardBoundsException("[ERROR] 더 이상 행을 감소할 수 없습니다.");
    }

    public int getValue() {
        return value;
    }

    public int subtract(Row other) {
        return value - other.value;
    }
}
