package domain.vo;

public class Position {

    private final int row;
    private final int col;

    private Position(final int row, final int col) {
        validate(row, col);
        this.row = row;
        this.col = col;
    }

    private void validate(int row, int col) {
        validateRowInRange(row);
        validateColInRange(col);
    }

    private void validateColInRange(int col) {
        if (0 > col || col > 8) {
            throw new IllegalArgumentException("[ERROR] 열이 0~8 범위를 벗어났습니다.");
        }
    }

    private void validateRowInRange(int row) {
        if (0 > row || row > 9) {
            throw new IllegalArgumentException("[ERROR] 행이 0~9 범위를 벗어났습니다.");
        }
    }

    public static Position of(final int row, final int col) {
        return new Position(row, col);
    }

    public int getRow() {
        return this.row;
    }

    public int getCol() {
        return this.col;
    }
}
