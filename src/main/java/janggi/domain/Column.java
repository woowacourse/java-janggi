package janggi.domain;

import janggi.exception.ColumnOutOfRangeException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Column {
    private static final int MIN = 0;
    private static final int MAX = 9;
    private static final Map<Integer, Column> CACHE = new HashMap<>();  // 직접 실험해서 성능 차이 확인

    static {
        for (int i = MIN; i <= MAX; i++) {
            CACHE.put(i, new Column(i));
        }
    }

    private final int value;

    private Column(int value) {
        this.value = value;
    }
    public static Column of(int value) {
        if (!CACHE.containsKey(value)) {
            throw new ColumnOutOfRangeException();
        }
        return CACHE.get(value);
    }

    public static Collection<Column> values() {
        return CACHE.values();
    }

    public int getColumn() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Column column = (Column) o;
        return this.value == column.value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
