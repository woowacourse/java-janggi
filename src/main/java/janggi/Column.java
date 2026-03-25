package janggi;

import java.util.Arrays;
import java.util.List;

public enum Column {
    OUT,
    ONE,
    TWO,
    THREE,
    FOUR,
    FIVE,
    SIX,
    SEVEN,
    EIGHT,
    NINE;

    private static final Column[] CACHE_VALUES = values();


    public Column next() {
        if (this == OUT) {
            return OUT;
        }
        return CACHE_VALUES[(this.ordinal() + 1) % CACHE_VALUES.length];
    }


    public Column previous() {
        if (this == OUT) {
            return OUT;
        }
        return CACHE_VALUES[(this.ordinal() - 1)];
    }

    public List<Column> to(Column other) {
        if (this == OUT || other == OUT) {
            throw new IllegalArgumentException("보드의 바깥 위치가 포함돼 있습니다.");
        }

        if (this.ordinal() > other.ordinal()) {
            List<Column> result = Arrays.asList(Arrays.copyOfRange(
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
