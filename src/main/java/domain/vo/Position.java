package domain.vo;

import java.util.Objects;

public class Position {

    private final int row;
    private final int col;

    private Position(final int row, final int col) {
        validate(row, col);
        this.row = row;
        this.col = col;
    }

    public static Position of(final int row, final int col) {
        return new Position(row, col);
    }

    public boolean isOneStepStraightTo(Position to) {
        return (this.row == to.row && Math.abs(this.col - to.col) == 1)
                || (this.col == to.col && Math.abs(this.row - to.row) == 1);
    }

    public boolean isOneStepDiagonalTo(Position to) {
        return Math.abs(this.row - to.row) == 1
                && Math.abs(this.col - to.col) == 1;
    }

    public boolean isStraightTo(Position to) {
        return (this.row == to.row) || (this.col == to.col);
    }

    public boolean isDiagonalTo(Position to) {
        return Math.abs(this.row - to.row) == Math.abs(this.col - to.col);
    }

    public int getDistanceRow(Position to) {
        return Math.abs(this.row - to.row);
    }

    public int getDistanceCol(Position to) {
        return Math.abs(this.col - to.col);
    }

    public int getRow() {
        return this.row;
    }

    public int getCol() {
        return this.col;
    }

    @Override
    public boolean equals(Object object) {
        if (object == null || getClass() != object.getClass()) return false;
        Position position = (Position) object;
        return row == position.row && col == position.col;
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, col);
    }

    private void validate(final int row, final int col) {
        validateRowInRange(row);
        validateColInRange(col);
    }

    private void validateRowInRange(final int row) {
        if (0 > row || row > 9) {
            throw new IllegalArgumentException("행이 0~9 범위를 벗어났습니다.");
        }
    }

    private void validateColInRange(final int col) {
        if (0 > col || col > 8) {
            throw new IllegalArgumentException("열이 0~8 범위를 벗어났습니다.");
        }
    }
}
