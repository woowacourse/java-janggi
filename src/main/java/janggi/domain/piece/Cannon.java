package janggi.domain.piece;

import janggi.domain.Team;
import janggi.domain.piece.movement.Movement;

import java.util.Map;

public class Cannon extends StraightMovingPiece {

    public Cannon(final Position position, final Team team) {
        super(PieceType.CANNON, position, team);
    }

    @Override
    public Piece from(Position position) {
        return new Cannon(position, getTeam());
    }

    @Override
    protected void validatePieceCondition(Map<Position, Piece> pieces, Position positionToMove, Movement direction) {
        Position currentPosition = getPosition();
        int count = 0;
        while(currentPosition.isNotEndPoint() && !currentPosition.equals(positionToMove)) {
            count += getIsNoneValue(pieces.get(currentPosition));
            currentPosition = currentPosition.plus(direction.getX(), direction.getY());
            validateIsCannon(pieces.get(currentPosition));
        }
        validateCount(count);
    }

    private void validateIsCannon(Piece piece) {
        if(piece.getPieceType() == PieceType.CANNON) {
            throw new IllegalArgumentException("포는 포를 잡지 못합니다");
        }
    }

    private void validateCount(int count) {
        if(count != 1) {
            throw new IllegalArgumentException("포는 하나의 기물만 건너 뛰어야 합니다");
        }
    }

    private int getIsNoneValue(Piece piece) {
        if(piece.isNone()) {
            return 0;
        }
        return 1;
    }
}
