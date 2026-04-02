package janggi.domain.piece.strategy;

import janggi.domain.Position;

public record DirectionInformation(int rowDistance, int colDistance) {

    public DirectionInformation(Position source, Position destination) {
        this(destination.calculateRowDistance(source), destination.calculateColumnDistance(source));
    }

    public int calculateRowDirection() {
        if (rowDistance == 0) {
            return 0;
        }
        return rowDistance / Math.abs(rowDistance);
    }

    public int calculateColDirection() {
        if (colDistance == 0) {
            return 0;
        }
        return colDistance / Math.abs(colDistance);
    }

    public int calculateDistance() {
        return Math.abs(rowDistance) + Math.abs(colDistance);
    }

    public boolean isRowBiggerThanCol() {
        return Math.abs(rowDistance) > Math.abs(colDistance);
    }

    public boolean isHorizontal() {
        return rowDistance == 0;
    }

    public boolean isVertical() {
        return colDistance == 0;
    }

    public boolean hasAbsDifferences(int distance1, int distance2) {
        int absRow = Math.abs(rowDistance);
        int absCol = Math.abs(colDistance);
        return (absRow == distance1 && absCol == distance2) || (absRow == distance2 && absCol == distance1);
    }
}
