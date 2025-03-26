package pieceProperty;

import static pieceProperty.Movement.DOWN;
import static pieceProperty.Movement.DOWN_LEFT_DOWN_DIAGONAL;
import static pieceProperty.Movement.DOWN_LEFT_DOWN_DIAGONAL_LEFT_DOWN_DIAGONAL;
import static pieceProperty.Movement.DOWN_RIGHT_DOWN_DIAGONAL;
import static pieceProperty.Movement.DOWN_RIGHT_DOWN_DIAGONAL_RIGHT_DOWN_DIAGONAL;
import static pieceProperty.Movement.LEFT;
import static pieceProperty.Movement.LEFT_DOWN_DIAGONAL;
import static pieceProperty.Movement.LEFT_LEFT_DOWN_DIAGONAL;
import static pieceProperty.Movement.LEFT_LEFT_DOWN_DIAGONAL_LEFT_DOWN_DIAGONAL;
import static pieceProperty.Movement.LEFT_LEFT_UP_DIAGONAL;
import static pieceProperty.Movement.LEFT_LEFT_UP_DIAGONAL_LEFT_UP_DIAGONAL;
import static pieceProperty.Movement.LEFT_UP_DIAGONAL;
import static pieceProperty.Movement.RIGHT;
import static pieceProperty.Movement.RIGHT_DOWN_DIAGONAL;
import static pieceProperty.Movement.RIGHT_RIGHT_DOWN_DIAGONAL;
import static pieceProperty.Movement.RIGHT_RIGHT_DOWN_DIAGONAL_RIGHT_DOWN_DIAGONAL;
import static pieceProperty.Movement.RIGHT_RIGHT_UP_DIAGONAL;
import static pieceProperty.Movement.RIGHT_RIGHT_UP_DIAGONAL_RIGHT_UP_DIAGONAL;
import static pieceProperty.Movement.RIGHT_UP_DIAGONAL;
import static pieceProperty.Movement.UP;
import static pieceProperty.Movement.UP_LEFT_UP_DIAGONAL;
import static pieceProperty.Movement.UP_LEFT_UP_DIAGONAL_LEFT_UP_DIAGONAL;
import static pieceProperty.Movement.UP_RIGHT_UP_DIAGONAL;
import static pieceProperty.Movement.UP_RIGHT_UP_DIAGONAL_RIGHT_UP_DIAGONAL;

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

    public Position calculateUpMovement() {
        return new Position(row + UP.getDRow(), col + UP.getDCol());
    }

    public Position calculateDownMovement() {
        return new Position(row + DOWN.getDRow(), col + DOWN.getDCol());
    }

    public Position calculateRightMovement() {
        return new Position(row + RIGHT.getDRow(), col + RIGHT.getDCol());
    }

    public Position calculateLeftMovement() {
        return new Position(row + LEFT.getDRow(), col + LEFT.getDCol());
    }

    public Position calculateRightUpMovement() {
        return new Position(row + RIGHT_UP_DIAGONAL.getDRow(), col + RIGHT_UP_DIAGONAL.getDCol());
    }

    public Position calculateRightDownMovement() {
        return new Position(row + RIGHT_DOWN_DIAGONAL.getDRow(), col + RIGHT_DOWN_DIAGONAL.getDCol());
    }

    public Position calculateLeftUpMovement() {
        return new Position(row + LEFT_UP_DIAGONAL.getDRow(), col + LEFT_UP_DIAGONAL.getDCol());
    }

    public Position calculateLeftDownMovement() {
        return new Position(row + LEFT_DOWN_DIAGONAL.getDRow(), col + LEFT_DOWN_DIAGONAL.getDCol());
    }

    public Position calculateUpRightUPMovement() {
        return new Position(row + UP_RIGHT_UP_DIAGONAL.getDRow(), col + UP_RIGHT_UP_DIAGONAL.getDCol());
    }

    public Position calculateUpLeftUpMovement() {
        return new Position(row + UP_LEFT_UP_DIAGONAL.getDRow(), col + UP_LEFT_UP_DIAGONAL.getDCol());
    }

    public Position calculateRightRightUpMovement() {
        return new Position(row + RIGHT_RIGHT_UP_DIAGONAL.getDRow(), col + RIGHT_RIGHT_UP_DIAGONAL.getDCol());
    }

    public Position calculateRightRightDownMovement() {
        return new Position(row + RIGHT_RIGHT_DOWN_DIAGONAL.getDRow(), col + RIGHT_RIGHT_DOWN_DIAGONAL.getDCol());
    }

    public Position calculateDownRightDownMovement() {
        return new Position(row + DOWN_RIGHT_DOWN_DIAGONAL.getDRow(), col + DOWN_RIGHT_DOWN_DIAGONAL.getDCol());
    }

    public Position calculateDownLeftDownMovement() {
        return new Position(row + DOWN_LEFT_DOWN_DIAGONAL.getDRow(), col + DOWN_LEFT_DOWN_DIAGONAL.getDCol());
    }

    public Position calculateLeftLeftUpMovement() {
        return new Position(row + LEFT_LEFT_UP_DIAGONAL.getDRow(), col + LEFT_LEFT_UP_DIAGONAL.getDCol());
    }

    public Position calculateLeftLeftDownMovement() {
        return new Position(row + LEFT_LEFT_DOWN_DIAGONAL.getDRow(), col + LEFT_LEFT_DOWN_DIAGONAL.getDCol());
    }

    public Position calculateUpRightUpRightUpMovement() {
        return new Position(row + UP_RIGHT_UP_DIAGONAL_RIGHT_UP_DIAGONAL.getDRow(),
                col + UP_RIGHT_UP_DIAGONAL_RIGHT_UP_DIAGONAL.getDCol());
    }

    public Position calculateUpLeftUpLeftUpMovement() {
        return new Position(row + UP_LEFT_UP_DIAGONAL_LEFT_UP_DIAGONAL.getDRow(),
                col + UP_LEFT_UP_DIAGONAL_LEFT_UP_DIAGONAL.getDCol());
    }

    public Position calculateRightRightUpRightUpMovement() {
        return new Position(row + RIGHT_RIGHT_UP_DIAGONAL_RIGHT_UP_DIAGONAL.getDRow(),
                col + RIGHT_RIGHT_UP_DIAGONAL_RIGHT_UP_DIAGONAL.getDCol());
    }

    public Position calculateRightRightDownRightDownMovement() {
        return new Position(row + RIGHT_RIGHT_DOWN_DIAGONAL_RIGHT_DOWN_DIAGONAL.getDRow(),
                col + RIGHT_RIGHT_DOWN_DIAGONAL_RIGHT_DOWN_DIAGONAL.getDCol());
    }

    public Position calculateDownRightDownRightDownMovement() {
        return new Position(row + DOWN_RIGHT_DOWN_DIAGONAL_RIGHT_DOWN_DIAGONAL.getDRow(),
                col + DOWN_RIGHT_DOWN_DIAGONAL_RIGHT_DOWN_DIAGONAL.getDCol());
    }

    public Position calculateDownLeftDownLeftDownMovement() {
        return new Position(row + DOWN_LEFT_DOWN_DIAGONAL_LEFT_DOWN_DIAGONAL.getDRow(),
                col + DOWN_LEFT_DOWN_DIAGONAL_LEFT_DOWN_DIAGONAL.getDCol());
    }

    public Position calculateLeftLeftUpLeftUpMovement() {
        return new Position(row + LEFT_LEFT_UP_DIAGONAL_LEFT_UP_DIAGONAL.getDRow(),
                col + LEFT_LEFT_UP_DIAGONAL_LEFT_UP_DIAGONAL.getDCol());
    }

    public Position calculateLeftLeftDownLeftDownMovement() {
        return new Position(row + LEFT_LEFT_DOWN_DIAGONAL_LEFT_DOWN_DIAGONAL.getDRow(),
                col + LEFT_LEFT_DOWN_DIAGONAL_LEFT_DOWN_DIAGONAL.getDCol());
    }

    public boolean isUpLeftUpLeftUp(final int dRow, final int dCol) {
        return dRow == 3 && dCol == 2;
    }

    public boolean isUpRightUpRightUp(final int dRow, final int dCol) {
        return dRow == 3 && dCol == -2;
    }

    public boolean isRightUpRightUpRight (final int dRow, final int dCol) {
        return dRow == 2 && dCol == -3;
    }

    public boolean isRightRightDownRightDown(final int dRow, final int dCol) {
        return dRow == -2 && dCol == -3;
    }

    public boolean isDownRightDownRightDown(final int dRow, final int dCol) {
        return dRow == -3 && dCol == -2;
    }

    public boolean isDownLeftDownLeftDown(final int dRow, final int dCol) {
        return dRow == -3 && dCol == 2;
    }

    public boolean isLeftLeftUpLeftUp(final int dRow, final int dCol) {
        return dRow == 2 && dCol == 3;
    }

    public boolean isLeftLeftDownLeftDown(final int dRow, final int dCol) {
        return dRow == -2 && dCol == 3;
    }

    public boolean isUpLeftUp(final int dRow, final int dCol) {
        return dRow == 2 && dCol == 1;
    }

    public boolean isUpRightUp(final int dRow, final int dCol) {
        return dRow == 2 && dCol == -1;
    }

    public boolean isRightRightUp(final int dRow, final int dCol) {
        return dRow == 1 && dCol == - 2;
    }

    public boolean isRightRightDown(final int dRow, final int dCol) {
        return dRow == -1 && dCol == -2;
    }

    public boolean isDownRightDown(final int dRow, final int dCol) {
        return dRow == -2 && dCol == -1;
    }

    public boolean isDownLeftDown(final int dRow, final int dCol) {
        return dRow == -2 && dCol == 1;
    }

    public boolean isLeftLeftDown(final int dRow, final int dCol) {
        return dRow == -1 && dCol == 2;
    }

    public boolean isLeftLeftUp(final int dRow, final int dCol) {
        return dRow == 1 && dCol == 2;
    }

    public boolean isLeftward(final int dRow, final int dCol) {
        return dRow == 0 && dCol > 0;
    }

    public boolean isRightward(final int dRow, final int dCol) {
        return dRow == 0 && dCol < 0;
    }

    public boolean isUpward(final int dRow, final int dCol) {
        return dRow > 0 && dCol == 0;
    }

    public boolean isDownward(final int dRow, final int dCol) {
        return dRow < 0 && dCol == 0;
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
