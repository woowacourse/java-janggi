package janggi.coordinate;

public record Distance(int rowDistance, int columnDistance) {

    public Distance(final int rowDistance, final int columnDistance) {
        this.rowDistance = rowDistance;
        this.columnDistance = columnDistance;
        validateSign();
    }

    public static Distance of(Position from, Position to) {
        Distance rowDistance = from.row().distanceTo(to.row());
        Distance columnDistance = from.column().distanceTo(to.column());
        return rowDistance.combine(columnDistance);
    }

    private void validateSign() {
        if (rowDistance < 0 || columnDistance < 0) {
            throw new IllegalArgumentException("거리는 음수가 될 수 없습니다");
        }
    }

    public Distance combine(Distance other) {
        boolean isNotRowConflict = this.rowDistance == 0 || other.rowDistance == 0;
        boolean isNotColumnConflict = this.columnDistance == 0 || other.columnDistance == 0;

        if (isNotRowConflict && isNotColumnConflict) {
            return new Distance(
                    this.rowDistance + other.rowDistance,
                    this.columnDistance + other.columnDistance
            );
        }

        throw new IllegalArgumentException("동일 축의 거리끼리는 합칠 수 없습니다");
    }

    public int getTotal() {
        return rowDistance + columnDistance;
    }

    public int getStraight() {
        return Math.abs(rowDistance - columnDistance);
    }

    public int getDiagonal() {
        return Math.min(rowDistance, columnDistance);
    }

    public boolean isVertical() {
        return rowDistance != 0 && columnDistance == 0;
    }

    public boolean isHorizontal() {
        return rowDistance == 0 && columnDistance != 0;
    }

    public boolean isStraight() {
        return isVertical() || isHorizontal();
    }
}
