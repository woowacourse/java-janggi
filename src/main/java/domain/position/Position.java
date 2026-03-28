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

    public static Position of(Row row, Column column) {
        return new Position(row, column);
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

    public List<Position> getVerticalPathExcludeDestination(Position destination) {
        Row min = row.getLower(destination.row);
        Row max = row.getUpper(destination.row);

        return IntStream.range(min.getValue() + 1, max.getValue())
                .mapToObj(row -> Position.of(row, column.getValue()))
                .toList();
    }

    public List<Position> getHorizontalPathExcludeDestination(Position destination) {
        Column min = column.getLower(destination.column);
        Column max = column.getUpper(destination.column);
        return IntStream.range(min.getValue() + 1, max.getValue())
                .mapToObj(column -> Position.of(row.getValue(), column))
                .toList();
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

    public Row getRow() {
        return row;
    }

    public Column getColumn() {
        return column;
    }
}
