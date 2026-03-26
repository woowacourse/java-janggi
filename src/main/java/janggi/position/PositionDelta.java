package janggi.position;

public record PositionDelta(
        int rowDistance,
        int columnDistance
) {

    public static PositionDelta between(Position from, Position to) {
        return new PositionDelta(
                to.getRowDistance(from),
                to.getColumnDistance(from)
        );
    }

    public PositionDelta movedHorizontally(int columnDistance) {
        return new PositionDelta(
                rowDistance,
                this.columnDistance - columnDistance
        );
    }

    public PositionDelta movedVertically(int rowDistance) {
        return new PositionDelta(
                this.rowDistance - rowDistance,
                columnDistance
        );
    }

    public boolean isMoreThanOneStep() {
        return getAbsRowDistance() + getAbsColumnDistance() < 2;
    }

    public boolean isMoreThanOneStepIncludingDiagonal() {
        return getAbsRowDistance() >= 2 || getAbsColumnDistance() >= 2;
    }

    private boolean isNotStraightThenDiagonal(int straight, int diagonal) {
        return (getAbsRowDistance() != straight || getAbsColumnDistance() != diagonal) &&
                (getAbsRowDistance() != diagonal || getAbsColumnDistance() != straight);
    }

    public boolean isMoreThanOneStepAndDiagonal() {
        return isNotStraightThenDiagonal(1, 2);
    }

    public boolean isMoreThanOneStepAndDoubleDiagonal() {
        return isNotStraightThenDiagonal(2, 3);
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

    public boolean isHorizontalLongerThanVertical() {
        return getAbsColumnDistance() > getAbsRowDistance();
    }

    public int getUnitDistance() {
        int unit = rowDistance;
        if (getAbsRowDistance() < getAbsColumnDistance()) {
            unit = columnDistance;
        }

        if (unit > 0) {
            return 1;
        }

        return -1;
    }
}
