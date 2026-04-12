package janggi.domain.position;

import java.util.Objects;
import java.util.Optional;

public class Position {
    private final Row row;
    private final Column column;

    private Position(Row row, Column column) {
        this.row = row;
        this.column = column;
    }

    public static Position of(int row, int column) {
        return new Position(Row.from(row), Column.from(column));
    }

    public Optional<Position> move(Direction direction) {
        Optional<Row> nextRow = row.move(direction.dr());
        Optional<Column> nextColumn = column.move(direction.dc());

        if (nextRow.isPresent() && nextColumn.isPresent()) {
            return Optional.of(new Position(nextRow.get(), nextColumn.get()));
        }

        return Optional.empty();
    }

    public int row() {
        return row.value();
    }

    public int column() {
        return column.value();
    }

    @Override
    public final boolean equals(Object o) {
        if (!(o instanceof Position position)) return false;

        return Objects.equals(row, position.row) && Objects.equals(column, position.column);
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, column);
    }
}
