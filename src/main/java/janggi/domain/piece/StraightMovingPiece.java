package janggi.domain.piece;

import janggi.domain.piece.movement.Movement;

import java.util.Map;

public abstract class StraightMovingPiece extends Piece{

    public StraightMovingPiece(String name, Position position, Team team) {
        super(name, position, team);
    }

    @Override
    public boolean canMoveTo(Map<Position, Piece> pieces, Position positionToMove) {
        Movement direction = findDirection(positionToMove);
        return checkPieceCondition(pieces, positionToMove, direction);
    }

    private Movement findDirection(Position positionToMove) {
        return Movement.getDiagonal(
                positionToMove.x() - getPosition().x(),
                positionToMove.y() - getPosition().y()
        );
    }

    protected abstract boolean checkPieceCondition(Map<Position, Piece> pieces, Position positionToMove, Movement direction);
}
