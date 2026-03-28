package janggi.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Position {
    private static final Map<String, Position> CACHE = new HashMap<>();

    static {
        for (Row row : Row.values()) {
            for (Column column : Column.values()) {
                CACHE.put(toKey(row, column), new Position(row, column));
            }
        }
    }

    private final Row x;
    private final Column y;

    private Position(Row x, Column y) {
        this.x = x;
        this.y = y;
    }

    public static Position of(Row x, Column y) {
        String key = toKey(x, y);
        return CACHE.get(key);
    }

    private static String toKey(Row x, Column y) {
        return x.getRow() + "," + y.getColumn();
    }

    public List<Integer> getPosition() {
        List<Integer> position = new ArrayList<>();
        position.add(x.getRow());
        position.add(y.getColumn());
        return position;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Position position = (Position) o;
        return Objects.equals(x, position.x) && Objects.equals(y, position.y);
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
