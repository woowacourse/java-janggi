package janggi.domain.board;

import java.util.ArrayList;
import java.util.List;

public class PiecePath {
    private final Position source;
    private final Position destination;

    public PiecePath(Position source, Position destination) {
        this.source = source;
        this.destination = destination;
    }

    public boolean isStraight() {
        return rowDifference() == 0 || columnDifference() == 0;
    }

    public int rowDifference() {
        return destination.rowValue() - source.rowValue();
    }

    public int columnDifference() {
        return destination.columnValue() - source.columnValue();
    }

    public Position getFractionalPosition(int divisor) {
        int newRow = source.rowValue() + rowDifference() / divisor;
        int newColumn = source.columnValue() + columnDifference() / divisor;

        return new Position(Row.from(newRow), Column.from(newColumn));
    }

    public Position getFactionalPositionToTarget(Position other, int divisor) {
        int newRow = other.rowValue() + rowDifference() / divisor;
        int newColumn = other.columnValue() + columnDifference() / divisor;

        return new Position(Row.from(newRow), Column.from(newColumn));
    }

    public List<Position> getBetweenPositions() {
        int rowDir = getDirection(rowDifference());
        int columnDir = getDirection(columnDifference());
        Direction direction = Direction.from(rowDir, columnDir);

        Position current = source.move(direction);
        List<Position> positions = new ArrayList<>();
        while(!current.equals(destination)) {
            positions.add(current);
            current = current.move(direction);
        }
        return positions;
    }

    public int getDirection(int difference) {
        if(difference != 0) {
            return difference / Math.abs(difference);
        }
        return 0;
    }
}
