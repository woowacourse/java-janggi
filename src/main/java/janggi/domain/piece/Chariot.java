package janggi.domain.piece;

import janggi.domain.Team;
import janggi.domain.piece.movement.Movement;

import java.util.Map;

public class Chariot extends StraightMovingPiece {

    public Chariot(final Position position, final Team team) {
        super(PieceType.CHARIOT, position, team);
    }

    @Override
    public Piece from(Position position) {
        return new Chariot(position, getTeam());
    }

    @Override
    protected void validatePieceCondition(Map<Position, Piece> pieces, Position positionToMove, Movement direction) {
        Position currentPosition = getPosition().plus(direction.getX(), direction.getY());
        while (currentPosition.isNotEndPoint() && !currentPosition.equals(positionToMove)) {
            validateIsNotNone(pieces, currentPosition);
            currentPosition = currentPosition.plus(direction.getX(), direction.getY());
        }
    }

    private static void validateIsNotNone(Map<Position, Piece> pieces, Position currentPosition) {
        if (pieces.get(currentPosition).isNotNone()) {
            throw new IllegalArgumentException("불가능한 이동입니다");
        }
    }
}
