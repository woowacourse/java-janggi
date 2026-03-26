package janggi.position;

public record PositionConnection(
        int rowDistance,
        int columnDistance
) {

    public static PositionConnection of(Position from, Position to) {
        return new PositionConnection(
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

    public int straightDistance() {
        return isHorizontalLonger() ? columnDistance : rowDistance;
    }

    public int diagonalDistance() {
        return isHorizontalLonger() ? rowDistance / getAbsColumnDistance()
                : columnDistance / getAbsColumnDistance();
    }

    public boolean isHorizontalLonger() {
        return getAbsColumnDistance() > getAbsRowDistance();
    }

}
