package janggi.domain.position;

import janggi.exception.business.RowOutOfRangeException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Row {
    private static final int MIN = 0;
    private static final int MAX = 9;
    private static final Map<Integer, Row> CACHE = new HashMap<>();

    static {
        for (int i = MIN; i <= MAX; i++) {
            CACHE.put(i, new Row(i));
        }
    }

    private final int value;

    private Row(int value) {
        this.value = value;
    }

    public static Row of(int value) {
        if (!CACHE.containsKey(value)) {
            throw new RowOutOfRangeException();
        }
        return CACHE.get(value);
    }

    static Collection<Row> values() {
        return CACHE.values();
    }

    public int getRow() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Row row = (Row) o;
        return this.value == row.value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
