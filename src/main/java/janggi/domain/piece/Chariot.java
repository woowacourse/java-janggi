package janggi.domain.piece;

import janggi.domain.piece.movement.Movement;

import java.util.Map;

public class Chariot extends StraightMovingPiece {

    public Chariot(final Position position, final Team team) {
        super("차", position, team);
    }

    @Override
    protected Movement findDirection(Position positionToMove) {
        return null;
    }

    @Override
    protected boolean checkPieceCondition(Map<Position, Piece> pieces, Position positionToMove, Movement direction) {
        Position currentPosition = getPosition();
        while (currentPosition.isNotEndPoint() || currentPosition.equals(positionToMove)) {
            if (pieces.get(currentPosition).isNotNone()) {
                throw new IllegalArgumentException("불가능한 이동입니다");
            }
            currentPosition = currentPosition.plus(direction.getX(), direction.getY());
        }
        return true;
    }

    @Override
    public Piece from(Position position) {
        return new Chariot(position, getTeam());
    }
}
