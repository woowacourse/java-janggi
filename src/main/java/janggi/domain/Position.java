package janggi.domain;

import janggi.exception.position.InvalidPositionException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Position {
    private static final Map<String, Position> CACHE = new HashMap<>();

    static {
        Row.values().stream()
                .flatMap(row -> Column.values().stream()
                        .map(column -> new Position(row, column)))
                .forEach(position -> CACHE.put(generateKey(position.row, position.column), position));
    }

    private final Row row;
    private final Column column;

    private Position(Row row, Column column) {
        this.row = row;
        this.column = column;
    }

    public static Position of(Row row, Column column) {
        String key = generateKey(row, column);
        Position position = CACHE.get(key);
        if (position == null) {
            throw new InvalidPositionException();
        }
        return position;
    }

    public static Position from(List<Integer> coordinates) {
        return of(Row.of(coordinates.get(0)), Column.of(coordinates.get(1)));
    }

    private static String generateKey(Row row, Column column) {
        return String.format("%d,%d", row.getValue(), column.getValue());
    }

    public int getRowValue() {
        return row.getValue();
    }

    public int getColumnValue() {
        return column.getValue();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Position position = (Position) o;
        return row == position.row && column == position.column;
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, column);
    }
}
