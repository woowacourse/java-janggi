package janggi.domain.piece.strategy;

import janggi.domain.Position;

public record DirectionInformation(int rowDifference, int colDifference) {

    public DirectionInformation(Position source, Position destination) {
        this(destination.calculateRowDistance(source), destination.calculateColumnDistance(source));
    }

    public int calculateAbsRowDifference() {
        return Math.abs(rowDifference);
    }

    public int calculateAbsColDifference() {
        return Math.abs(colDifference);
    }

    public int calculateRowDirection() {
        return Integer.signum(rowDifference);
    }

    public int calculateColDirection() {
        return Integer.signum(colDifference);
    }

    public boolean isRowBiggerThanCol() {
        return Math.abs(rowDifference) > Math.abs(colDifference);
    }

    public int addAllDifference() {
        return rowDifference + colDifference;
    }
}
