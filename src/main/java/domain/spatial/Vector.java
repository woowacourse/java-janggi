package domain.spatial;

public record Vector(
        int moveRow,
        int moveColumn
) {

    private static final int MAX_MOVE_ROW = 1;
    private static final int MIN_MOVE_ROW = -1;
    private static final int MAX_MOVE_COLUMN = 1;
    private static final int MIN_MOVE_COLUMN = -1;

    public Vector {
        validateRange(moveRow, moveColumn);
    }

    private void validateRange(final int moveRow, final int moveColumn) {
        if (moveRow > MAX_MOVE_ROW || moveColumn > MAX_MOVE_COLUMN || moveRow < MIN_MOVE_ROW
                || moveColumn < MIN_MOVE_COLUMN) {
            throw new IllegalArgumentException("[ERROR] 이동 방향의 수치는 -1, 0, 1만 가능합니다.");
        }
    }
}
