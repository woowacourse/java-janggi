package janggi.model.position;

public record DiagonalDelta(
        int rowDistance,
        int columnDistance
) {
    private static final int UNIT_STEP = 1;
    private static final int NEGATIVE_UNIT_STEP = -1;
    private static final int NO_MOVEMENT = 0;

    public DiagonalDelta {

        if (Math.abs(rowDistance) != Math.abs(columnDistance)
                || rowDistance == NO_MOVEMENT) {
            throw new IllegalArgumentException("대각선이 아닙니다.");
        }
    }

    public boolean isNorth() {
        return rowDistance < NO_MOVEMENT;
    }

    public boolean isEast() {
        return columnDistance > NO_MOVEMENT;
    }

    public int getRowUnitDistance() {
        if (rowDistance < NO_MOVEMENT) {
            return NEGATIVE_UNIT_STEP;
        }
        return UNIT_STEP;
    }

    public int getColumnUnitDistance() {
        if (columnDistance < NO_MOVEMENT) {
            return NEGATIVE_UNIT_STEP;
        }
        return UNIT_STEP;
    }

    public int getCountOfUnitDiagonal() {
        return Math.abs(rowDistance);
    }
}
