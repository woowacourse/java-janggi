package janggi.domain.piece.strategy;

import janggi.domain.Position;
import janggi.domain.board.BoardChecker;
import janggi.exception.ExceptionMessage;

public class GeneralStrategy extends PalaceStrategy {

    private static final int DISTANCE = 1;


    @Override
    public void validate(Position source, Position destination, BoardChecker board) {
        Movement movement = new Movement(source, destination);
        if (!isPalaceRange(source, destination)) {
            throw new IllegalArgumentException(ExceptionMessage.INVALID_PALACE_MOVE.getMessage(DISTANCE));
        }
        validatePalaceDistance(movement);
    }
}
