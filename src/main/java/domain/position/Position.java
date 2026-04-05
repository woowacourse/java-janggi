package domain.position;

import domain.PieceExceptionMessage;
import domain.piece.strategy.Direction;
import java.util.ArrayList;
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

    public boolean isSameRow(Position destination) {
        return row.equals(destination.row);
    }

    public boolean isSameColumn(Position destination) {
        return column.equals(destination.column);
    }

    public List<Position> getPathToDestination(List<Direction> direction, Position destination) {
        for (Direction dir : direction) {
            Position current = this;
            List<Position> path = new ArrayList<>();

            while (dir.isMovable(current)) {
                current = dir.getMovedPosition(current);
                path.add(current);
                if (current.equals(destination)) {
                    return path;
                }
            }
        }
        throw new IllegalArgumentException(PieceExceptionMessage.INVALID_POSITION.getMessage());
    }

    public List<Position> getVerticalPathExcludeDestination(Position destination) {
        Row min = row.getLower(destination.row);
        Row max = row.getUpper(destination.row);

        return IntStream.range(min.getValue() + 1, max.getValue())
                .mapToObj(row -> Position.of(row, column.getValue()))
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
