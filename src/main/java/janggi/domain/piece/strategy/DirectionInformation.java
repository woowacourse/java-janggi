package janggi.domain.piece.strategy;

import janggi.domain.Position;

public record DirectionInformation(int rowDifference, int colDifference) {

    public DirectionInformation(Position source, Position destination) {
        this(destination.calculateRowDistance(source), destination.calculateColumnDistance(source));
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

    public int calculateDistance() {
        return Math.abs(rowDifference) + Math.abs(colDifference);
    }

    public boolean isRowBiggerThanCol() {
        return Math.abs(rowDifference) > Math.abs(colDifference);
    }

    public boolean isHorizontal() {
        return rowDifference == 0;
    }

    public boolean isVertical() {
        return colDifference == 0;
    }

    public boolean hasAbsDifferences(int difference1, int difference2) {
        int absRow = Math.abs(rowDifference);
        int absCol = Math.abs(colDifference);
        return (absRow == difference1 && absCol == difference2) || (absRow == difference2 && absCol == difference1);
    }
}
