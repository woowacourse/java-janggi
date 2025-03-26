package janggi.domain.piece;

import janggi.domain.piece.movement.Movement;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Guard extends PathMovingPiece {


    public Guard(final Position position, final Team team) {
        super("사", position, team);
    }

    @Override
    protected List<Movement> findMovements(Position positionToMove) {
        return List.of(Movement.getDiagonal(
                positionToMove.x() - getPosition().x(),
                positionToMove.y() - getPosition().y()
        ));
    }

    @Override
    protected boolean checkPieceCondition(Piece pieceInPositionToMove, Position checkingPosition) {
        return pieceInPositionToMove.isNone() &&
                checkingPosition.isPalace();
    }

    @Override
    public Piece from(Position position) {
        return new Guard(position, team);
    }
}
