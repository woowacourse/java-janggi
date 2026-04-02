package janggi.domain.position;

import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

public class Column {
    private static final int MAX_SIZE = 8;
    private static final List<Column> ALL_COLUMN = IntStream.rangeClosed(0, MAX_SIZE)
            .mapToObj(Column::new)
            .toList();

    private final int value;

    private Column(int value) {
        this.value = value;
    }

    public static Column from(int value) {
        validate(value);
        return ALL_COLUMN.get(value);
    }

    private static void validate(int value) {
        if (isOutOfBounds(value)) {
            throw new IllegalArgumentException("열은 0 ~ 8 입니다.");
        }
    }

    private static boolean isOutOfBounds(int value) {
        return value < 0 || value > MAX_SIZE;
    }

    public Optional<Column> move(int distance) {
        int nextValue = this.value + distance;

        if (isOutOfBounds(nextValue)) {
            return Optional.empty();
        }
        return Optional.of(ALL_COLUMN.get(nextValue));
    }

    int value() {
        return value;
    }

    @Override
    public final boolean equals(Object o) {
        if (!(o instanceof Column column)) return false;

        return value == column.value;
    }

    @Override
    public int hashCode() {
        return value;
    }
}
