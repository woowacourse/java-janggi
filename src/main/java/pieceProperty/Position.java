package pieceProperty;

import java.util.Objects;

public class Position {

    private final int row;
    private final int col;

    public Position(final int row, final int col) {
        validateOutOfBound(row, col);
        this.row = row;
        this.col = col;
    }

    public Position calculateMovement(int dRow, int dCol) {
        return new Position(row + dRow, col + dCol);
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    private void validateOutOfBound(final int row, final int col) {
        if (row > 9 || col > 10 || row < 0 || col < 0) {
            throw new IllegalArgumentException("[ERROR] 장기판은 10 x 9 입니다. 범위를 초과하였습니다.");
        }
    }

    @Override
    public boolean equals(final Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final Position that = (Position) o;
        return getRow() == that.getRow() && getCol() == that.getCol();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getRow(), getCol());
    }
}
