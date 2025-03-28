package janggi.domain.piece;

import janggi.domain.Team;
import janggi.domain.piece.movement.Movement;

import java.util.Map;

public abstract class StraightMovingPiece extends Piece{

    public StraightMovingPiece(PieceType pieceType, Position position, Team team) {
        super(pieceType, position, team);
    }

    @Override
    public void validatePositionToMove(Map<Position, Piece> pieces, Position positionToMove) {
        Movement direction = findDirection(positionToMove);
        validatePieceCondition(pieces, positionToMove, direction);
    }

    protected Movement findDirection(Position positionToMove) {
        if(getPosition().isInSameDiagonalInPalace(positionToMove)) {
            return Movement.getDiagonal(
                    positionToMove.x() - getPosition().x(),
                    positionToMove.y() - getPosition().y()
            );
        }
        return Movement.getOrthogonal(
                positionToMove.x() - getPosition().x(),
                positionToMove.y() - getPosition().y()
        );
    }

    protected abstract void validatePieceCondition(Map<Position, Piece> pieces, Position positionToMove, Movement direction);
}
