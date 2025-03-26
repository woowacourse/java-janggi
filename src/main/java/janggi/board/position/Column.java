package janggi.board.position;

import janggi.board.PositionOutOfBoardBoundsException;
import java.util.Arrays;

public enum Column {
    ZERO(0),
    ONE(1),
    TWO(2),
    THREE(3),
    FOUR(4),
    FIVE(5),
    SIX(6),
    SEVEN(7),
    EIGHT(8);

    public static final int SIZE = 9;

    private final int value;

    Column(final int value) {
        validate(value);
        this.value = value;
    }

    public static Column from(int value) {
        return Arrays.stream(values())
                .filter(column -> column.value == value)
                .findAny()
                .orElseThrow(() -> new PositionOutOfBoardBoundsException("[ERROR] 올바르지 않은 열입니다."));
    }

    private void validate(int value) {
        if (value < 0 || value >= SIZE) {
            throw new PositionOutOfBoardBoundsException("[ERROR] 올바르지 않은 열입니다.");
        }
    }

    public boolean canUp() {
        return value < SIZE - 1;
    }

    public boolean canDown() {
        return value > 0;
    }

    public Column up() {
        if (canUp()) {
            return from(value + 1);
        }
        throw new PositionOutOfBoardBoundsException("[ERROR] 더 이상 열을 증가할 수 없습니다.");
    }

    public Column down() {
        if (canDown()) {
            return from(value - 1);
        }
        throw new PositionOutOfBoardBoundsException("[ERROR] 더 이상 열을 감소할 수 없습니다.");
    }

    public int subtract(Column column) {
        return value - column.value;
    }
}
