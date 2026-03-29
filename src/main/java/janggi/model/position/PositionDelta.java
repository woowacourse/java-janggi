package janggi.model.position;

public record PositionDelta(
        int rowDistance,
        int columnDistance
) {

    private static final int UNIT_STEP = 1;
    private static final int NEGATIVE_UNIT_STEP = -1;
    private static final int NO_MOVEMENT = 0;
    private static final int SECOND_STEP = 2;

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

    public boolean isMultiStep() {
        return getAbsRowDistance() >= SECOND_STEP || getAbsColumnDistance() >= SECOND_STEP;
    }

    public boolean notMatchStepPattern(int rowDistance, int columnDistance) {
        return (getAbsRowDistance() != rowDistance || getAbsColumnDistance() != columnDistance) &&
                (getAbsRowDistance() != columnDistance || getAbsColumnDistance() != rowDistance);
    }

    public boolean isHorizontal() {
        return rowDistance == NO_MOVEMENT;
    }

    public boolean isVertical() {
        return columnDistance == NO_MOVEMENT;
    }

    public boolean isHorizontalDominant() {
        return getAbsColumnDistance() > getAbsRowDistance();
    }

    public int getStepSign() {
        int unit = rowDistance;

        if (getAbsRowDistance() < getAbsColumnDistance()) {
            unit = columnDistance;
        }

        if (unit > NO_MOVEMENT) {
            return UNIT_STEP;
        }

        return NEGATIVE_UNIT_STEP;
    }

    private int getAbsRowDistance() {
        return Math.abs(rowDistance);
    }

    private int getAbsColumnDistance() {
        return Math.abs(columnDistance);
    }
}
