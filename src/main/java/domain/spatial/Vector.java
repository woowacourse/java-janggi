package domain.spatial;

public record Vector(
        int moveRow,
        int moveColumn
) {

    public Vector {
        validateRange(moveRow, moveColumn);
    }

    public boolean isDiagonal() {
        return moveRow != 0 && moveColumn != 0;
    }

    private void validateRange(final int moveRow, final int moveColumn) {
        if (isInvalidMove(moveRow) || isInvalidMove(moveColumn)) {
            throw new IllegalArgumentException("이동 방향의 수치는 -1, 0, 1만 가능합니다.");
        }
    }

    private boolean isInvalidMove(final int move) {
        return move != -1 && move != 0 && move != 1;
    }
}
