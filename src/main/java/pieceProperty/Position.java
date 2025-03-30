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

public class Position {

    private static final Position TOP_LEFT = new Position(0, 3);
    private static final Position TOP_MID = new Position(0, 4);
    private static final Position TOP_RIGHT = new Position(0, 5);
    private static final Position TOP_BOTTOM_LEFT = new Position(2, 3);
    private static final Position TOP_BOTTOM_MID = new Position(2, 4);
    private static final Position TOP_BOTTOM_RIGHT = new Position(2, 5);
    private static final Position TOP_MID_LEFT = new Position(1, 3);
    private static final Position TOP_CENTER = new Position(1, 4);
    private static final Position TOP_MID_RIGHT = new Position(1, 5);

    private static final Position BOTTOM_TOP_LEFT = new Position(7, 3);
    private static final Position BOTTOM_TOP_MID = new Position(7, 4);
    private static final Position BOTTOM_TOP_RIGHT = new Position(7, 5);
    private static final Position BOTTOM_MID_LEFT = new Position(8, 3);
    private static final Position BOTTOM_CENTER = new Position(8, 4);
    private static final Position BOTTOM_MID_RIGHT = new Position(8, 5);
    private static final Position BOTTOM_LEFT = new Position(9, 3);
    private static final Position BOTTOM_MID = new Position(9, 4);
    private static final Position BOTTOM_RIGHT = new Position(9, 5);

    private final int row;
    private final int col;

    public Position(final int row, final int col) {
        this.row = row;
        this.col = col;
    }

    public boolean isInHanPalace() {
        return this.equals(TOP_LEFT)
                || this.equals(TOP_MID)
                || this.equals(TOP_RIGHT)
                || this.equals(TOP_BOTTOM_LEFT)
                || this.equals(TOP_BOTTOM_MID)
                || this.equals(TOP_BOTTOM_RIGHT)
                || this.equals(TOP_MID_LEFT)
                || this.equals(TOP_MID_RIGHT)
                || this.equals(TOP_CENTER);
    }

    public boolean isInChoPalace() {
        return this.equals(BOTTOM_TOP_LEFT)
                || this.equals(BOTTOM_TOP_MID)
                || this.equals(BOTTOM_TOP_RIGHT)
                || this.equals(BOTTOM_MID_LEFT)
                || this.equals(BOTTOM_CENTER)
                || this. equals(BOTTOM_MID_RIGHT)
                || this.equals(BOTTOM_LEFT)
                || this.equals(BOTTOM_MID)
                || this.equals(BOTTOM_RIGHT);
    }

    public boolean isOneStepDiagonalMoveForHanOmniDirectionMover(Position destination) {
        return this.equals(TOP_LEFT) && destination.equals(TOP_CENTER) ||
                this.equals(TOP_CENTER) && destination.equals(TOP_LEFT) ||

                this.equals(TOP_CENTER) && destination.equals(TOP_RIGHT) ||
                this.equals(TOP_RIGHT) && destination.equals(TOP_CENTER) ||

                this.equals(TOP_CENTER) && destination.equals(TOP_BOTTOM_LEFT) ||
                this.equals(TOP_BOTTOM_LEFT) && destination.equals(TOP_CENTER) ||

                this.equals(TOP_CENTER) && destination.equals(TOP_BOTTOM_RIGHT) ||
                this.equals(TOP_BOTTOM_RIGHT) && destination.equals(TOP_CENTER);
    }

    public boolean isOneStepDiagonalMoveForChoOmniDirectionMover(Position destination) {
        return this.equals(BOTTOM_TOP_LEFT) && destination.equals(BOTTOM_CENTER)
                || this.equals(BOTTOM_CENTER) && destination.equals(BOTTOM_TOP_LEFT)

                || this.equals(BOTTOM_CENTER) && destination.equals(BOTTOM_TOP_RIGHT)
                || this.equals(BOTTOM_TOP_RIGHT) && destination.equals(BOTTOM_CENTER)

                || this. equals(BOTTOM_CENTER) && destination.equals(BOTTOM_LEFT)
                || this.equals(BOTTOM_LEFT) && destination.equals(BOTTOM_CENTER)

                || this.equals(BOTTOM_CENTER) && destination.equals(BOTTOM_RIGHT)
                || this.equals(BOTTOM_RIGHT) && destination.equals(BOTTOM_CENTER);
    }

    public boolean isTwoStepDiagonalMoveForLinearMoverInCho(Position destination) {
        return this.equals(BOTTOM_LEFT) && destination.equals(BOTTOM_TOP_RIGHT)
                || this.equals(BOTTOM_TOP_RIGHT) && destination.equals(BOTTOM_LEFT)

                || this.equals(BOTTOM_RIGHT) && destination.equals(BOTTOM_TOP_LEFT)
                || this.equals(BOTTOM_TOP_LEFT) && this.equals(BOTTOM_RIGHT);
    }

    public boolean isTwoStepDiagonalMoveForLinearMoverInHan(Position destination) {
        return this.equals(TOP_LEFT) && destination.equals(TOP_BOTTOM_RIGHT)
                || this.equals(TOP_BOTTOM_RIGHT) && destination.equals(TOP_LEFT)

                || this.equals(TOP_BOTTOM_LEFT) && destination.equals(TOP_RIGHT)
                || this.equals(TOP_RIGHT) && destination.equals(TOP_BOTTOM_LEFT);
    }

    public boolean isDiagonalMoveForLinearMover(Position destination) {
        return this.isOneStepDiagonalMoveForHanOmniDirectionMover(destination)
                || this.isOneStepDiagonalMoveForChoOmniDirectionMover(destination)
                || this.isTwoStepDiagonalMoveForLinearMoverInCho(destination)
                || this.isTwoStepDiagonalMoveForLinearMoverInHan(destination);
    }

    public Position calculateMovement(final int dRow, final int dCol) {
        return new Position(row + dRow, col + dCol);
    }

    public boolean isSameRow(final Position destination) {
        return row == destination.getRow();
    }

    public boolean isSameCol(final Position destination) {
        return col == destination.col;
    }

    public int calculateDRow(final Position destination) {
        return row - destination.row;
    }

    public int calculateDCol(final Position destination) {
        return col - destination.col;
    }

    public boolean isUpMovementTo(Position destination) {
        return new Position(row + UP.getDRow(), col + UP.getDCol()).equals(destination);
    }

    public boolean isDownMovementTo(Position destination) {
        return new Position(row + DOWN.getDRow(), col + DOWN.getDCol()).equals(destination);
    }

    public boolean isRightMovementTo(Position destination) {
        return new Position(row + RIGHT.getDRow(), col + RIGHT.getDCol()).equals(destination);
    }

    public boolean isLeftMovementTo(Position destination) {
        return new Position(row + LEFT.getDRow(), col + LEFT.getDCol()).equals(destination);
    }

    public boolean isUpRightUpMovementTo(Position destination) {
        return new Position(row + UP_RIGHT_UP_DIAGONAL.getDRow(), col + UP_RIGHT_UP_DIAGONAL.getDCol()).equals(destination);
    }

    public boolean isUpLeftUpMovementTo(Position destination) {
        return new Position(row + UP_LEFT_UP_DIAGONAL.getDRow(), col + UP_LEFT_UP_DIAGONAL.getDCol()).equals(destination);
    }

    public boolean isRightRightUpMovementTo(Position destination) {
        return new Position(row + RIGHT_RIGHT_UP_DIAGONAL.getDRow(), col + RIGHT_RIGHT_UP_DIAGONAL.getDCol()).equals(destination);
    }

    public boolean isRightRightDownMovementTo(Position destination) {
        return new Position(row + RIGHT_RIGHT_DOWN_DIAGONAL.getDRow(), col + RIGHT_RIGHT_DOWN_DIAGONAL.getDCol()).equals(destination);
    }

    public boolean isDownRightDownMovementTo(Position destination) {
        return new Position(row + DOWN_RIGHT_DOWN_DIAGONAL.getDRow(), col + DOWN_RIGHT_DOWN_DIAGONAL.getDCol()).equals(destination);
    }

    public boolean isDownLeftDownMovementTo(Position destination) {
        return new Position(row + DOWN_LEFT_DOWN_DIAGONAL.getDRow(), col + DOWN_LEFT_DOWN_DIAGONAL.getDCol()).equals(destination);
    }

    public boolean isLeftLeftUpMovementTo(Position destination) {
        return new Position(row + LEFT_LEFT_UP_DIAGONAL.getDRow(), col + LEFT_LEFT_UP_DIAGONAL.getDCol()).equals(destination);
    }

    public boolean isLeftLeftDownMovementTo(Position destination) {
        return new Position(row + LEFT_LEFT_DOWN_DIAGONAL.getDRow(), col + LEFT_LEFT_DOWN_DIAGONAL.getDCol()).equals(destination);
    }

    public boolean isUpRightUpRightUpMovementTo(Position destination) {
        return new Position(row + UP_RIGHT_UP_DIAGONAL_RIGHT_UP_DIAGONAL.getDRow(),
                col + UP_RIGHT_UP_DIAGONAL_RIGHT_UP_DIAGONAL.getDCol()).equals(destination);
    }

    public boolean isUpLeftUpLeftUpMovementTo(Position destination) {

        return new Position(row + UP_LEFT_UP_DIAGONAL_LEFT_UP_DIAGONAL.getDRow(),
                col + UP_LEFT_UP_DIAGONAL_LEFT_UP_DIAGONAL.getDCol()).equals(destination);
    }

    public boolean isRightRightUpRightUpMovementTo(Position destination) {
        return new Position(row + RIGHT_RIGHT_UP_DIAGONAL_RIGHT_UP_DIAGONAL.getDRow(),
                col + RIGHT_RIGHT_UP_DIAGONAL_RIGHT_UP_DIAGONAL.getDCol()).equals(destination);
    }

    public boolean isRightRightDownRightDownMovementTo(Position destination) {
        return new Position(row + RIGHT_RIGHT_DOWN_DIAGONAL_RIGHT_DOWN_DIAGONAL.getDRow(),
                col + RIGHT_RIGHT_DOWN_DIAGONAL_RIGHT_DOWN_DIAGONAL.getDCol()).equals(destination);
    }

    public boolean isDownRightDownRightDownMovementTo(Position destination) {
        return new Position(row + DOWN_RIGHT_DOWN_DIAGONAL_RIGHT_DOWN_DIAGONAL.getDRow(),
                col + DOWN_RIGHT_DOWN_DIAGONAL_RIGHT_DOWN_DIAGONAL.getDCol()).equals(destination);
    }

    public boolean isDownLeftDownLeftDownMovementTo(Position destination) {
        return new Position(row + DOWN_LEFT_DOWN_DIAGONAL_LEFT_DOWN_DIAGONAL.getDRow(),
                col + DOWN_LEFT_DOWN_DIAGONAL_LEFT_DOWN_DIAGONAL.getDCol()).equals(destination);
    }

    public boolean isLeftLeftUpLeftUpMovementTo(Position destination) {
        return new Position(row + LEFT_LEFT_UP_DIAGONAL_LEFT_UP_DIAGONAL.getDRow(),
                col + LEFT_LEFT_UP_DIAGONAL_LEFT_UP_DIAGONAL.getDCol()).equals(destination);
    }

    public boolean isLeftLeftDownLeftDownMovementTo(Position destination) {
        return new Position(row + LEFT_LEFT_DOWN_DIAGONAL_LEFT_DOWN_DIAGONAL.getDRow(),
                col + LEFT_LEFT_DOWN_DIAGONAL_LEFT_DOWN_DIAGONAL.getDCol()).equals(destination);
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

    public boolean isLeftwardTo(Position destination) {
        int dRow = row - destination.row;
        int dCol = col - destination.col;
        return dRow == 0 && dCol > 0;
    }

    public boolean isRightwardTo(Position destination) {
        int dRow = row - destination.row;
        int dCol = col - destination.col;
        return dRow == 0 && dCol < 0;
    }

    public boolean isUpwardTo(Position destination) {
        int dRow = row - destination.row;
        int dCol = col - destination.col;
        return dRow > 0 && dCol == 0;
    }

    public boolean isDownwardTo(Position destination) {
        int dRow = row - destination.row;
        int dCol = col - destination.col;
        return dRow < 0 && dCol == 0;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
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
