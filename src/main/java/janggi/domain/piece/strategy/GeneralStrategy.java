package janggi.domain.piece.strategy;

import janggi.domain.Position;
import janggi.domain.board.BoardChecker;
import janggi.exception.ExceptionMessage;

public class GeneralStrategy extends PalaceStrategy {

    private static final int DISTANCE = 1;

    @Override
    protected void validatePalaceMove(Position source, Position destination, Movement movement, BoardChecker board) {
        validatePalaceStepDistance(movement, DISTANCE);
        validatePalaceDiagonalPath(source, destination, movement);
    }

    private void validatePalaceDiagonalPath(Position source, Position destination, Movement movement) {
        if (movement.isDiagonalLine() && !isPalaceDiagonalPath(source, destination, movement)) {
            throw new IllegalArgumentException(ExceptionMessage.INVALID_PALACE_MOVE.getMessage(DISTANCE));
        }
    }

    @Override
    protected void validateNormalMove(Position source, Position destination, Movement movement, BoardChecker board) {
        throw new IllegalArgumentException(ExceptionMessage.INVALID_PALACE_MOVE.getMessage(DISTANCE));
    }
}
