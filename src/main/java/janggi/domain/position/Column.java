package janggi.domain.position;

import java.util.List;
import java.util.stream.IntStream;

public class Column {
    private static final int MAX_SIZE = 8;

    private final int value;

    Column(int value) {
        validate(value);
        this.value = value;
    }

    static List<Column> all() {
        return IntStream.rangeClosed(0, MAX_SIZE)
                .mapToObj(Column::new)
                .toList();
    }

    private void validate(int value) {
        if (value < 0 || value > MAX_SIZE) {
            throw new IllegalArgumentException("열은 0 ~ 8 입니다.");
        }
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
