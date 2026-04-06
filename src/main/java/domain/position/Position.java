package domain.position;

import java.util.List;
import java.util.Objects;
import java.util.stream.IntStream;

public class Position {
    private final Row row;
    private final Column column;

    private Position(Row row, Column column) {
        this.row = row;
        this.column = column;
    }

    public static Position of(int row, int column) {
        return new Position(new Row(row), new Column(column));
    }

    public Position go(int row, int column) {
        return new Position(this.row.add(row), this.column.add(column));
    }

    public boolean isSameRow(Row destination) {
        return row.equals(destination);
    }

    public boolean isSameColumn(Column destination) {
        return column.equals(destination);
    }

    public boolean isRowInRange(int start, int end) {
        return row.isRowInRange(start, end);
    }

    public boolean isColumnInRange(int start, int end) {
        return column.isColumnInRange(start, end);
    }

    public List<Position> getSameColumnPositionsToDestination(Position destination) {
        Row min = row.getLowerValue(destination.row);
        Row max = row.getUpper(destination.row);

        return IntStream.range(min.value() + 1, max.value())
                .mapToObj(i -> Position.of(i, column.value()))
                .toList();
    }

    public List<Position> getSameRowPositionsToDestination(Position destination) {
        Column min = column.getLowerValue(destination.column);
        Column max = column.getUpper(destination.column);
        return IntStream.range(min.value() + 1, max.value())
                .mapToObj(i -> Position.of(row.value(), i))
                .toList();
    }

    public Row getRow() {
        return row;
    }

    public Column getColumn() {
        return column;
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
        return Objects.equals(row, position.row) && Objects.equals(column, position.column);
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, column);
    }
}
