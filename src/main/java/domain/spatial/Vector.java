package domain.spatial;

public class Vector {

    private static final int MAX_MOVE_ROW = 1;
    private static final int MIN_MOVE_ROW = -1;
    private static final int MAX_MOVE_COLUMN = 1;
    private static final int MIN_MOVE_COLUMN = -1;

    private final int moveRow;
    private final int moveColumn;

    public Vector(final int moveRow, final int moveColumn) {
        validateRange(moveRow, moveColumn);
        this.moveRow = moveRow;
        this.moveColumn = moveColumn;
    }

    public Position applyTo(Position position) {
        return new Position(position.row() + moveRow, position.column() + moveColumn);
    }

    private void validateRange(final int moveRow, final int moveColumn) {
        if (moveRow > MAX_MOVE_ROW || moveColumn > MAX_MOVE_COLUMN || moveRow < MIN_MOVE_ROW
                || moveColumn < MIN_MOVE_COLUMN) {
            throw new IllegalArgumentException("[ERROR] 이동 방향의 수치는 -1, 0, 1만 가능합니다.");
        }
    }
}
