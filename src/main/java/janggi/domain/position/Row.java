package janggi.domain.position;

import java.util.List;
import java.util.stream.IntStream;

public class Row {
    private static final int MAX_SIZE = 9;

    private final int value;

    Row(int value) {
        validate(value);
        this.value = value;
    }

    static List<Row> all() {
        return IntStream.rangeClosed(0, MAX_SIZE)
                .mapToObj(Row::new)
                .toList();
    }

    private void validate(int value) {
        if (value < 0 || value > MAX_SIZE) {
            throw new IllegalArgumentException("행은 0 ~ 9 입니다.");
        }
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
