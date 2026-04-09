package janggi.domain;

import janggi.exception.position.ColumnOutOfRangeException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Column {
    private static final int MIN = 0;
    private static final int MAX = 9;
    private static final List<Column> CACHE;

    static {
        CACHE = initializeCache();
    }

    private static List<Column> initializeCache() {
        List<Column> temp = new ArrayList<>();
        for (int i = MIN; i <= MAX; i++) {
            temp.add(new Column(i));
        }
        return Collections.unmodifiableList(temp);
    }

    private final int value;

    private Column(int value) {
        this.value = value;
    }

    public static Column of(int value) {
        if (value < MIN || value > MAX) {
            throw new ColumnOutOfRangeException();
        }
        return CACHE.get(value);
    }

    public static List<Column> values() {
        return CACHE;
    }

    public int getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Column column = (Column) o;
        return this.value == column.value;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(value);
    }
}
