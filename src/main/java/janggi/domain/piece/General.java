package janggi.domain.piece;

import janggi.domain.piece.movement.Movement;

import java.util.List;

public class General extends PathMovingPiece {

    public General(final Position position, final Team team) {
        super("궁", position, team);
    }

    @Override
    protected List<Movement> findMovements(Position positionToMove) {
        if (getPosition().isInSameDiagonalInPalace(positionToMove)) {
            return List.of(
                    Movement.getDiagonal(
                            positionToMove.x() - getPosition().x(),
                            positionToMove.y() - getPosition().y()
                    )
            );
        }
        return List.of(Movement.getOrthogonal(
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
        return new General(position, team);
    }
}
