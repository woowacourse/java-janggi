package janggi.position;

import java.util.Objects;

public class Position{
    private final Column column;
    private final Row row;

    public Position(Column column, Row row) {
        this.column = column;
        this.row = row;
    }

    public Position move(Row row, Column column) {
        return new Position(column.move(column), row.move(row));
    }

    public Position move(Position other) {
        return new Position(column.move(other.column), row.move(other.row));
    }

    public Position multiply(Position other) {
        // return new Position(x * other.x, y * other.y);
        return null;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final Position position = (Position) o;
        return column == position.column && row == position.row;
    }

    @Override
    public int hashCode() {
        return Objects.hash(column, row);
    }
}
