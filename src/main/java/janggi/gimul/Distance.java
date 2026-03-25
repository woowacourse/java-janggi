package janggi.gimul;

import janggi.Position;

public record Distance(
        int rowDistance,
        int columnDistance
) {

    public static Distance of(Position from, Position to) {
        return new Distance(
                to.getRowDistance(from),
                to.getColumnDistance(from)
        );
    }

    public boolean isMoreThanOneStep() {
        return getAbsRowDistance() + getAbsColumnDistance() < 2;
    }

    public boolean isMoreThanOneStepIncludingDiagonal() {
        return getAbsRowDistance() >= 2 || getAbsColumnDistance() >= 2;
    }

    private int getAbsRowDistance() {
        return Math.abs(rowDistance);
    }

    private int getAbsColumnDistance() {
        return Math.abs(columnDistance);
    }

    public boolean isHorizontal() {
        return rowDistance == 0;
    }

    public boolean isVertical() {
        return columnDistance == 0;
    }
}
