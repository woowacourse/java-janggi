package janggi.model.position;

public record PositionDelta(
        int rowDistance,
        int columnDistance
) {

    private static final int UNIT_STEP = 1;
    private static final int NEGATIVE_UNIT_STEP = -1;
    private static final int NO_MOVEMENT = 0;

    private static final int FIRST_STEP = 1;
    private static final int SECOND_STEP = 2;
    private static final int THIRD_STEP = 3;

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
        return getAbsRowDistance() + getAbsColumnDistance() < SECOND_STEP;
    }

    public boolean isMoreThanOneStepIncludingDiagonal() {
        return getAbsRowDistance() >= 2 || getAbsColumnDistance() >= SECOND_STEP;
    }

    private boolean isNotStraightThenDiagonal(int straight, int diagonal) {
        return (getAbsRowDistance() != straight || getAbsColumnDistance() != diagonal) &&
                (getAbsRowDistance() != diagonal || getAbsColumnDistance() != straight);
    }

    public boolean isMoreThanOneStepAndDiagonal() {
        return isNotStraightThenDiagonal(FIRST_STEP, SECOND_STEP);
    }

    public boolean isMoreThanOneStepAndDoubleDiagonal() {
        return isNotStraightThenDiagonal(SECOND_STEP, THIRD_STEP);
    }

    private int getAbsRowDistance() {
        return Math.abs(rowDistance);
    }

    private int getAbsColumnDistance() {
        return Math.abs(columnDistance);
    }

    public boolean isHorizontal() {
        return rowDistance == NO_MOVEMENT;
    }

    public boolean isVertical() {
        return columnDistance == NO_MOVEMENT;
    }

    public boolean isHorizontalLongerThanVertical() {
        return getAbsColumnDistance() > getAbsRowDistance();
    }

    public int getUnitDistance() {
        int unit = rowDistance;
        if (getAbsRowDistance() < getAbsColumnDistance()) {
            unit = columnDistance;
        }

        if (unit > NO_MOVEMENT) {
            return UNIT_STEP;
        }

        return NEGATIVE_UNIT_STEP;
    }
}
