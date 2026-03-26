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
}
