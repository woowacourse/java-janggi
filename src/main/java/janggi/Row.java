package janggi;

import java.util.Arrays;
import java.util.List;

public enum Row {
    OUT,
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

    private static final Row[] CACHE_VALUES = values();

    public static Row of(int ordinal) {
        return CACHE_VALUES[ordinal];
    }

    public Row previous() {
        if (this == OUT) {
            return OUT;
        }

        return CACHE_VALUES[this.ordinal() - 1];
    }

    public Row next() {
        if (this == OUT) {
            return OUT;
        }

        return CACHE_VALUES[(this.ordinal() + 1) % CACHE_VALUES.length];
    }

    public List<Row> to(Row other) {
        if (this == OUT || other == OUT) {
            throw new IllegalArgumentException("보드의 바깥 위치가 포함돼 있습니다.");
        }

        if (this.ordinal() > other.ordinal()) {
            List<Row> result = Arrays.asList(Arrays.copyOfRange(
                    CACHE_VALUES,
                    other.ordinal(),
                    this.ordinal() + 1
            ));

            return result.reversed();
        }

        return Arrays.asList(Arrays.copyOfRange(
                CACHE_VALUES,
                this.ordinal(),
                other.ordinal() + 1)
        );
    }
}
