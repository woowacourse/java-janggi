package janggi.domain.piece.strategy;

import janggi.domain.Position;
import janggi.domain.board.BoardChecker;
import janggi.domain.piece.camp.CampType;
import janggi.domain.piece.PieceRule;
import janggi.exception.ExceptionMessage;

public class GeneralStrategy extends PalaceStrategy implements MoveStrategy {

    private static final int DISTANCE = 1;


    @Override
    public void validate(Position source, Position destination, CampType campType, BoardChecker board, PieceRule pieceRule) {
        Movement movement = new Movement(source, destination);
        if (!isPalaceRange(source, destination)) {
            throw new IllegalArgumentException(ExceptionMessage.INVALID_PALACE_MOVE.getMessage(DISTANCE));
        }
        validatePalaceDistance(movement);
    }
}
