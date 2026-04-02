package janggi.domain.position;

import java.util.HashMap;
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

    private final Row row;
    private final Column column;

    private Position(Row row, Column column) {
        this.row = row;
        this.column = column;
    }

    public static Position of(Row row, Column column) {
        String key = toKey(row, column);
        return CACHE.get(key);
    }

    private static String toKey(Row row, Column column) {
        return row.getRow() + "," + column.getColumn();
    }

    public int getRow() {
        return row.getRow();
    }

    public int getColumn() {
        return column.getColumn();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Position position = (Position) o;
        return Objects.equals(row, position.row) && Objects.equals(column, position.column);
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, column);
    }
}
