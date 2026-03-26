package janggi.position;

import java.util.Arrays;
import java.util.List;

public enum Column {
    ONE,
    TWO,
    THREE,
    FOUR,
    FIVE,
    SIX,
    SEVEN,
    EIGHT,
    NINE;

    private static final List<Column> CACHE_VALUES = Arrays.asList(values());

    public static Column of(int ordinal) {
        return CACHE_VALUES.get(ordinal);
    }

    public Column moved(int displacement) {
        int nextValue = this.ordinal() + displacement;

        if (nextValue > NINE.ordinal() ||nextValue < ONE.ordinal()) {
            throw new IllegalArgumentException("보드 밖으로는 이동할 수 없습니다.");
        }

        return CACHE_VALUES.get(nextValue);
    }

    public List<Column> to(Column other) {
        if (this.ordinal() > other.ordinal()) {
            return CACHE_VALUES.subList(other.ordinal(), this.ordinal() + 1).reversed();
        }

        return CACHE_VALUES.subList(this.ordinal(), other.ordinal() + 1);

    }

    public int getDistance(Column other) {
        return this.ordinal() - other.ordinal();
    }
}
