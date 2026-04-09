package janggi.domain;

import janggi.exception.position.RowOutOfRangeException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Row {
    private static final int MIN = 0;
    private static final int MAX = 8;
    private static final List<Row> CACHE;

    static {
        CACHE = initializeCache();
    }

    private static List<Row> initializeCache() {
        List<Row> temp = new ArrayList<>();
        for (int i = MIN; i <= MAX; i++) {
            temp.add(new Row(i));
        }
        return Collections.unmodifiableList(temp);
    }

    private final int value;

    private Row(int value) {
        this.value = value;
    }

    public static Row of(int value) {
        if (value < MIN || value > MAX) {
            throw new RowOutOfRangeException();
        }
        return CACHE.get(value);
    }

    public static List<Row> values() {
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

        Row row = (Row) o;
        return this.value == row.value;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(value);
    }
}
