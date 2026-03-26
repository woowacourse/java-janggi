package janggi.domain.piece.strategy;

import janggi.domain.Position;

public record DirectionInformation(int rowDifference, int colDifference) {

    public DirectionInformation(Position from, Position to) {
        this(to.calculateRowDistance(from), to.calculateColumnDistance(from));
    }

    public int calculateRowDirection() {
        return rowDifference / Math.abs(rowDifference);
    }

    public int calculateColDirection() {
        return colDifference / Math.abs(colDifference);
    }

    public boolean isRowBiggerThanCol() {
        return Math.abs(rowDifference) > Math.abs(colDifference);
    }
}
