package janggi.domain.piece.strategy;

import janggi.domain.Position;
import janggi.exception.ExceptionMessage;

public abstract class PalaceStrategy implements MoveStrategy {

    private final Position CHO_GENERAL_POSITION = new Position(1, 4);
    private final Position HAN_GENERAL_POSITION = new Position(8, 4);

    private final int DISTANCE = 1;

    public boolean isPalaceRange(Position source, Position destination) {
        return isRowInRange(source) && isColInRange(source)
                && isRowInRange(destination) && isColInRange(destination);
    }

    private static boolean isRowInRange(Position position) {
        return (0 <= position.row() && position.row() <= 2) || (7 <= position.row() && position.row() <= 9);
    }

    private boolean isColInRange(Position source) {
        return 3 <= source.column() && source.column() <= 5;
    }

    public void validatePalaceDistance(Movement movement) {
        if (!movement.isValidMoveDistance(1, 1) && !movement.isValidMoveDistance(0, 1)) {
            throw new IllegalArgumentException(ExceptionMessage.INVALID_PALACE_MOVE.getMessage(DISTANCE));
        }
    }

    public void validatePalaceDiagonalDistance(Position source, Position destination, Movement movement) {
        if (!(source.equals(CHO_GENERAL_POSITION) || destination.equals(CHO_GENERAL_POSITION)
                || source.equals(HAN_GENERAL_POSITION) || destination.equals(HAN_GENERAL_POSITION))) {
            throw new IllegalArgumentException(ExceptionMessage.INVALID_PALACE_MOVE.getMessage(DISTANCE));
        }
        if (!movement.isValidMoveDistance(1, 1)) {
            throw new IllegalArgumentException(ExceptionMessage.INVALID_PALACE_MOVE.getMessage(DISTANCE));
        }
    }
}
