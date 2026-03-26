package janggi.position;

public record DiagonalDelta(
        int rowDistance,
        int columnDistance
) {
    public DiagonalDelta {
        if (Math.abs(rowDistance) != Math.abs(columnDistance)
                || rowDistance == 0) {
            throw new IllegalArgumentException("대각선이 아닙니다.");
        }
    }

    public boolean isNorth() {
        return rowDistance < 0;
    }

    public boolean isEast() {
        return columnDistance > 0;
    }

    public int getRowUnitDistance() {
        if (rowDistance < 0) {
            return -1;
        }
        return 1;
    }

    public int getColumnUnitDistance() {
        if (columnDistance < 0) {
            return -1;
        }
        return 1;
    }

    public int getCountOfUnitDiagonal() {
        return Math.abs(rowDistance);
    }
}
