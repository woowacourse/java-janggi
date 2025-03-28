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

    public int getAbsRowDifference() {
        return Math.abs(source.rowDifference(destination));
    }

    public int getAbsColumnDifference() {
        return Math.abs(source.columnDifference(destination));
    }

    public Position getDestination() {
        return destination;
    }

    public boolean isStraight() {
        return getRowDifference() == 0 || getColumnDifference() == 0;
    }

    public boolean isStraightMoveBy(int distance) {
        return (getAbsRowDifference() == 0 && getAbsColumnDifference() == distance)
                || (getAbsRowDifference() == distance && getAbsColumnDifference() == 0);
    }

    public boolean isDiagonalMoveBy(int distance) {
        if (!crossPalaceCenter()) {
            return false;
        }
        return (getAbsRowDifference() == distance && getAbsColumnDifference() == distance);
    }

    public boolean isStraightAndDiagonalMoveBy(int straightDistance, int diagonalDistance) {
        return (getAbsRowDifference() == straightDistance + diagonalDistance
                && getAbsColumnDifference() == diagonalDistance)
                || (getAbsRowDifference() == diagonalDistance
                && getAbsColumnDifference() == straightDistance + diagonalDistance);
    }

    public boolean isUpward() {
        return (getRowDifference() < 0);
    }

    public boolean isDownward() {
        return (getRowDifference() > 0);
    }

    public boolean isOutsidePalace() {
        return !source.isInPalace() || !destination.isInPalace();
    }

    public boolean crossPalaceCenter() {
        return source.isCenterOfPalace() || destination.isCenterOfPalace()
                || getPositionByFraction(2).isCenterOfPalace();
    }
}
