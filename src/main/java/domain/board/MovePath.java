package domain.board;

import java.util.ArrayList;
import java.util.List;

public class MovePath {

    private final Position source;
    private final Position destination;

    public MovePath(Position source, Position destination) {
        this.source = source;
        this.destination = destination;
    }

    public Position getPositionByFraction(int divisor) {
        int newRow = source.rowValue() + (getRowDifference() / divisor);
        int newColumn = source.columnValue() + (getColumnDifference() / divisor);

        return new Position(Row.from(newRow), Column.from(newColumn));
    }

    public List<Position> getBetweenPositions() {
        int rowDirection = getDirection(getRowDifference());
        int columnDirection = getDirection(getColumnDifference());

        Position currentPosition = updatePosition(source, rowDirection, columnDirection);
        List<Position> positions = new ArrayList<>();
        while (!currentPosition.equals(destination)) {
            positions.add(currentPosition);
            currentPosition = updatePosition(currentPosition, rowDirection, columnDirection);
        }
        return positions;
    }

    private int getDirection(int difference) {
        if (difference != 0) {
            return difference / Math.abs(difference);
        }
        return 0;
    }

    private Position updatePosition(Position currentPosition, int rowDir, int columnDir) {
        int newRow = currentPosition.rowValue() + rowDir;
        int newCol = currentPosition.columnValue() + columnDir;
        return new Position(Row.from(newRow), Column.from(newCol));
    }

    public int getRowDifference() {
        return source.rowDifference(destination);
    }

    public int getColumnDifference() {
        return source.columnDifference(destination);
    }

    public Position getDestination() {
        return destination;
    }
}
