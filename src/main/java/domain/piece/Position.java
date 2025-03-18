package domain.piece;

public class Position {

    public static final int MIN_ROW = 1;
    public static final int MIN_COLUMN = 1;
    public static final int MAX_ROW = 9;
    public static final int MAX_COLUMN = 10;

    private final int row;
    private final int column;

    public Position(final int row, final int column) {
        validateRange(row, column);
        this.row = row;
        this.column = column;
    }

    private void validateRange(final int row, final int column) {
        if (row < MIN_ROW || column < MIN_COLUMN || row > MAX_ROW || column > MAX_COLUMN) {
            throw new IllegalArgumentException("[ERROR] 좌표 입력은 9X10 보드 이내만 가능합니다.");
        }
    }
}
