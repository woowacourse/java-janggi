package domain.spatial;

public record Position(
        int row,
        int column
) {

    public static final int MIN_ROW = 1;
    public static final int MIN_COLUMN = 1;
    public static final int MAX_ROW = 9;
    public static final int MAX_COLUMN = 10;

    public Position {
        validateRange(row, column);
    }

    public Position moveBy(final Vector vector) {
        return vector.applyTo(this);
    }

    public boolean isMoveValid(final Vector vector) {
        try {
            vector.applyTo(this);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    private void validateRange(final int row, final int column) {
        if (row < MIN_ROW || column < MIN_COLUMN || row > MAX_ROW || column > MAX_COLUMN) {
            throw new IllegalArgumentException("[ERROR] 좌표 입력은 9X10 보드 이내만 가능합니다.");
        }
    }
}
