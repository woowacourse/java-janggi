package janggi;

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


    public Column next() {
        if (this == NINE) {
            throw new IllegalStateException("보드 밖으로는 이동할 수 없습니다.");
        }

        return CACHE_VALUES.get(this.ordinal() + 1);
    }


    public Column previous() {
        if (this == ONE) {
            throw new IllegalStateException("보드 밖으로는 이동할 수 없습니다.");
        }
        return CACHE_VALUES.get(this.ordinal() - 1);
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
