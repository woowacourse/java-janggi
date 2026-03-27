package model.position;

import java.util.Objects;
import model.move.Direction;

public record Position(Row row, Column column) {
    public static Position of(int x, int y) {
        return new Position(Row.from(x), Column.from(y));
    }

    public Position move(Direction direction) {
        return Position.of(row.move(direction), column.move(direction));
    }

    public boolean isSamePosition(Position to) {
        return this == to;
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
