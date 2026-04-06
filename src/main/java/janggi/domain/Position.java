package janggi.domain;

import janggi.exception.position.InvalidPositionException;
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
                CACHE.put(generateKey(row, column), new Position(row, column));
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
        return String.format("%d,%d", row.getRow(), column.getColumn());
    }

    public int getRowValue() {  // 이거 활용하도록 수정하기
        return row.getRow();
    }

    public int getColumnValue() {
        return column.getColumn();
    }

    public List<Integer> getPosition() {    // Position을 활용
        List<Integer> position = new ArrayList<>();
        position.add(row.getRow());   // 캡슐화 깨짐
        position.add(column.getColumn());
        return position;
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
