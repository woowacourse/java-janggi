package janggi.model.position;

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

    public static Row of(int rowNumber) {
        int adjustValue = 1;
        int zeroInput = 0;
        int maxRow = 10;

        if (rowNumber == zeroInput) {
            rowNumber = maxRow;
        }

        return CACHE_VALUES.get(rowNumber - adjustValue);
    }

    public Row moved(int displacement) {
        int nextValue = this.ordinal() + displacement;

        if (nextValue > ZERO.ordinal() || nextValue < ONE.ordinal()) {
            throw new IllegalArgumentException("보드 밖으로는 이동할 수 없습니다.");
        }

        return CACHE_VALUES.get(nextValue);
    }

    public List<Row> to(Row other) {
        int adjustValue = 1;
        if (this.ordinal() > other.ordinal()) {
            return CACHE_VALUES.subList(other.ordinal(), this.ordinal() + adjustValue).reversed();
        }

        return CACHE_VALUES.subList(this.ordinal(), other.ordinal() + adjustValue);
    }

    public int getDistance(Row other) {
        return this.ordinal() - other.ordinal();
    }
}
