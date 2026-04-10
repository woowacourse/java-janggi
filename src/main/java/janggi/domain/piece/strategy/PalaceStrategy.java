package janggi.domain.piece.strategy;

import janggi.domain.Position;
import janggi.domain.board.BoardChecker;
import janggi.exception.ExceptionMessage;

public abstract class PalaceStrategy implements MoveStrategy {

    protected static final Position CHO_PALACE_CENTER = new Position(1, 4);
    protected static final Position HAN_PALACE_CENTER = new Position(8, 4);

    protected static final int CHO_ROW_MIN = 0;
    protected static final int CHO_ROW_MAX = 2;
    protected static final int HAN_ROW_MIN = 7;
    protected static final int HAN_ROW_MAX = 9;

    protected static final int COL_MIN = 3;
    protected static final int COL_MAX = 5;

    @Override
    public final void validate(Position source, Position destination, BoardChecker board) {
        Movement movement = new Movement(source, destination);
        if (isPalaceRange(source, destination)) {
            validatePalaceMove(source, destination, movement, board);
            return;
        }
        validateNormalMove(source, destination, movement, board);
    }


    protected abstract void validatePalaceMove(Position source, Position destination, Movement movement, BoardChecker board);

    protected abstract void validateNormalMove(Position source, Position destination, Movement movement, BoardChecker board);

    protected boolean isPalaceRange(Position source, Position destination) {
        return isRowInRange(source) && isColInRange(source)
                && isRowInRange(destination) && isColInRange(destination);
    }

    private boolean isRowInRange(Position position) {
        return (CHO_ROW_MIN <= position.row() && position.row() <= CHO_ROW_MAX)
                || (HAN_ROW_MIN <= position.row() && position.row() <= HAN_ROW_MAX);
    }

    private boolean isColInRange(Position source) {
        return COL_MIN <= source.column() && source.column() <= COL_MAX;
    }

    protected boolean isPalaceDiagonalPath(Position source, Position destination, Movement movement) {
        if (!movement.isDiagonalLine()) {
            return false;
        }
        return isPalaceDiagonalPoint(source) && isPalaceDiagonalPoint(destination);
    }

    private boolean isPalaceDiagonalPoint(Position position) {
        return position.equals(CHO_PALACE_CENTER) || isPalaceCorner(position, CHO_ROW_MIN, CHO_ROW_MAX)
                || position.equals(HAN_PALACE_CENTER) || isPalaceCorner(position, HAN_ROW_MIN, HAN_ROW_MAX);
    }

    private boolean isPalaceCorner(Position position, int rowMin, int rowMax) {
        return (position.row() == rowMin || position.row() == rowMax)
                && (position.column() == COL_MIN || position.column() == COL_MAX);
    }

    protected void validatePalaceStepDistance(Movement movement, int maxDistance) {
        if (Math.abs(movement.rowDistance()) > maxDistance || Math.abs(movement.colDistance()) > maxDistance) {
            throw new IllegalArgumentException(ExceptionMessage.INVALID_PALACE_MOVE.getMessage(maxDistance));
        }
    }
}
