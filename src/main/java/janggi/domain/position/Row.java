package janggi.domain.position;

import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

public class Row {
    private static final int MAX_SIZE = 9;
    private static final List<Row> ALL_ROW = IntStream.rangeClosed(0, MAX_SIZE)
            .mapToObj(Row::new)
            .toList();

    private final int value;

    private Row(int value) {
        this.value = value;
    }

    public static Row from(int value) {
        validate(value);
        return ALL_ROW.get(value);
    }

    private static void validate(int value) {
        if (isOutOfBounds(value)) {
            throw new IllegalArgumentException("행은 0 ~ 9 입니다.");
        }
    }

    private static boolean isOutOfBounds(int value) {
        return value < 0 || value > MAX_SIZE;
    }

    public Optional<Row> move(int distance) {
        int nextValue = this.value + distance;

        if (isOutOfBounds(nextValue)) {
            return Optional.empty();
        }

        return Optional.ofNullable(ALL_ROW.get(nextValue));
    }

    int value() {
        return value;
    }

    @Override
    public final boolean equals(Object o) {
        if (!(o instanceof Row row)) return false;

        return value == row.value;
    }

    @Override
    public int hashCode() {
        return value;
    }
}
