package pieceProperty;

import java.util.Objects;
import view.ErrorMessage;

public class Position {

    private static final int MAX_ROW = 9;
    private static final int MAX_COL = 10;
    private static final int MIN_ROW = 0;
    private static final int MIN_COL = 0;

    private final int row;
    private final int col;

    public Position(final int row, final int col) {
        validateOutOfBound(row, col);
        this.row = row;
        this.col = col;
    }

    public Position calculateMovement(final int dRow, final int dCol) {
        return new Position(row + dRow, col + dCol);
    }

    public Boolean isSameRow(final Position destination) {
        return row == destination.getRow();
    }

    public Boolean isSameCol(final Position destination) {
        return col == destination.col;
    }

    public int calculateDRow(final Position destination) {
        return row - destination.row;
    }

    public int calculateDCol(final Position destination) {
        return col - destination.col;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    private void validateOutOfBound(final int row, final int col) {
        if (row > MAX_ROW || col > MAX_COL || row < MIN_ROW || col < MIN_COL) {
            throw new IllegalArgumentException(ErrorMessage.formatMessage("장기판은 10 x 9 입니다. 범위를 초과하였습니다."));
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
