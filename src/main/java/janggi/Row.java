package janggi;

import java.util.Arrays;
import java.util.List;

public enum Row {
    ONE,
    TWO,
    THREE,
    FOUR,
    FIVE,
    SIX,
    SEVEN,
    EIGHT,
    NINE,
    ZERO;

    private static final List<Row> CACHE_VALUES = Arrays.asList(values());

    public static Row of(int ordinal) {
        return CACHE_VALUES.get(ordinal);
    }

    public Row previous() {
        if (this == ONE) {
            throw new IllegalStateException("보드 밖으로는 이동할 수 없습니다.");
        }

        return CACHE_VALUES.get(this.ordinal() - 1);
    }

    public Row next() {
        if (this == ZERO) {
            throw new IllegalStateException("보드 밖으로는 이동할 수 없습니다.");
        }

        return CACHE_VALUES.get(this.ordinal() + 1);
    }

    public List<Row> to(Row other) {
        if (this.ordinal() > other.ordinal()) {
            return CACHE_VALUES.subList(other.ordinal(), this.ordinal() + 1).reversed();
        }

        return CACHE_VALUES.subList(this.ordinal(), other.ordinal() + 1);
    }

    public int getDistance(Row other) {
        return this.ordinal() - other.ordinal();
    }
}
