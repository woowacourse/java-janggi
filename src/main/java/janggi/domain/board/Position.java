package janggi.domain.board;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Position {
    private final Row row;
    private final Column column;

    public Position(Row row, Column column) {
        this.row = row;
        this.column = column;
    }

    public int rowDifference(Position other) {
        return other.rowValue() - row.intValue();
    }

    public int columnDifference(Position other) {
        return other.columnValue() - column.getValue();
    }

    public Position getPositionByFraction(Position other, int divisor) {
        int newRow = row.intValue() + rowDifference(other) / divisor;
        int newColumn = column.getValue() + columnDifference(other) / divisor;

        return new Position(Row.from(newRow), Column.from(newColumn));
    }

    public List<Position> getBetweenPositions(Position other) {
        int rowDir = getDirection(rowDifference(other));
        int columnDir = getDirection(columnDifference(other));

        Position currentPosition = updatePosition(new Position(row, column), rowDir, columnDir);
        List<Position> positions = new ArrayList<>();
        while(!currentPosition.equals(other)) {
            positions.add(currentPosition);
            currentPosition = updatePosition(currentPosition, rowDir, columnDir);
        }
        return positions;
    }

    private int getDirection(int difference) {
        if(difference != 0) {
            return difference / Math.abs(difference);
        }
        return 0;
    }

    private Position updatePosition(Position currentPosition, int rowDir, int columnDir) {
        int newRow = currentPosition.rowValue() + rowDir;
        int newCol = currentPosition.columnValue() + columnDir;
        currentPosition = new Position(Row.from(newRow), Column.from(newCol));
        return currentPosition;
    }

    public int rowValue() {
        return row.intValue();
    }

    public int columnValue() {
        return column.getValue();
    }

    @Override
    public String toString() {
        return row.intValue() + ", " + column.getValue();
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Position position)) return false;
        return row == position.row && column == position.column;
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, column);
    }
}
