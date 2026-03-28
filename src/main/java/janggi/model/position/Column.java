package janggi.model.position;

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

    public static Column of(int columnNumber) {
        if (columnNumber < 1 || columnNumber > 9) {
            throw new IllegalArgumentException("해당 열은 존재하지 않습니다.");
        }
        int adjustValue = 1;
        return CACHE_VALUES.get(columnNumber - adjustValue);
    }

    public Column moved(int displacement) {
        int nextValue = this.ordinal() + displacement;

        if (nextValue > NINE.ordinal() || nextValue < ONE.ordinal()) {
            throw new IllegalArgumentException("보드 밖으로는 이동할 수 없습니다.");
        }

        return CACHE_VALUES.get(nextValue);
    }
    public List<Column> to(Column other) {
        int adjustValue = 1;
        if (this.ordinal() > other.ordinal()) {
            return CACHE_VALUES.subList(other.ordinal(), this.ordinal() + adjustValue).reversed();
        }

        return CACHE_VALUES.subList(this.ordinal(), other.ordinal() + adjustValue);

    }

    public int getDistance(Column other) {
        return this.ordinal() - other.ordinal();
    }
}
