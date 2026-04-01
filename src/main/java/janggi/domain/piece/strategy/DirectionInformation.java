package janggi.domain.piece.strategy;

import janggi.domain.Position;

public record DirectionInformation(int rowDifference, int colDifference) {

    public DirectionInformation(Position source, Position destination) {
        this(destination.calculateRowDistance(source), destination.calculateColumnDistance(source));
    }

    public int calculateDistance() {
        return calculateAbsRowDifference() + calculateAbsColDifference();
    }

    public int calculateAbsRowDifference() {
        return Math.abs(rowDifference);
    }

    public int calculateAbsColDifference() {
        return Math.abs(colDifference);
    }

    public int calculateRowDirection() {
        if (rowDifference == 0) {
            return 0;
        }
        return rowDifference / Math.abs(rowDifference);
    }

    public int calculateColDirection() {
        if (colDifference == 0) {
            return 0;
        }
        return colDifference / Math.abs(colDifference);
    }

    public boolean isRowBiggerThanCol() {
        return Math.abs(rowDifference) > Math.abs(colDifference);
    }

    public int addAllDifference() {
        return rowDifference + colDifference;
    }
}
