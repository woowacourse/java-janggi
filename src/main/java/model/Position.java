package model;

import java.util.Objects;

public record Position(Row row, Column column) {
    public static Position of(int x, int y) {
        return new Position(Row.from(x), Column.from(y));
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Position position = (Position) o;
        return Objects.equals(row, position.row) && Objects.equals(column, position.column);
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, column);
    }
}
