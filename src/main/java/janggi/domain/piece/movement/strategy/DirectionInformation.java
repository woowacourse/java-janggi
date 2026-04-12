package janggi.domain.piece.movement.strategy;

import janggi.domain.board.Position;

public record DirectionInformation(int rowDifference, int columnDifference) {

    public DirectionInformation(Position source, Position destination) {
        this(destination.calculateRowDistance(source), destination.calculateColumnDistance(source));
    }

    public int calculateAbsRowDifference() {
        return Math.abs(rowDifference);
    }

    public int calculateAbsColumnDifference() {
        return Math.abs(columnDifference);
    }

    public int calculateRowDirection() {
        return Integer.signum(rowDifference);
    }

    public int calculateColumnDirection() {
        return Integer.signum(columnDifference);
    }

    public boolean isRowBiggerThanColumn() {
        return Math.abs(rowDifference) > Math.abs(columnDifference);
    }

    public int addAllDifference() {
        return rowDifference + columnDifference;
    }
}
