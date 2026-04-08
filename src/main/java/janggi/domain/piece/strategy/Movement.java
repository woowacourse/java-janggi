package janggi.domain.piece.strategy;

import janggi.domain.Position;

public record Movement(int rowDistance, int colDistance) {

    public Movement(Position source, Position destination) {
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

    public boolean isRowBiggerThanCol() {
        return Math.abs(rowDistance) > Math.abs(colDistance);
    }

    public boolean isHorizontal() {
        return rowDistance == 0;
    }

    public boolean isVertical() {
        return colDistance == 0;
    }

    public boolean isDiagonalLine() {
        return Math.abs(rowDistance) == Math.abs(colDistance) && rowDistance != 0;
    }

    public boolean isStraight() {
        return isHorizontal() || isVertical();
    }

    public boolean isValidMoveDistance(int firstDistance, int secondDistance) {
        int absRow = Math.abs(rowDistance);
        int absCol = Math.abs(colDistance);

        return (absRow == firstDistance && absCol == secondDistance)
                || (absRow == secondDistance && absCol == firstDistance);
    }
}
