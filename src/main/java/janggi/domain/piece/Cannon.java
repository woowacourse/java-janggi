package janggi.domain.piece;

import janggi.domain.piece.movement.Movement;

import java.util.Map;

public class Cannon extends StraightMovingPiece {

    public Cannon(final Position position, final Team team) {
        super("포", position, team);
    }

    @Override
    protected boolean checkPieceCondition(Map<Position, Piece> pieces, Position positionToMove, Movement direction) {
        Position currentPosition = getPosition();
        int count = 0;
        while(currentPosition.isNotEndPoint() && !currentPosition.equals(positionToMove)) {
            if(pieces.get(currentPosition).isNotNone()) {
                count ++;
            }
            currentPosition = currentPosition.plus(direction.getX(), direction.getY());
            if(pieces.get(currentPosition) instanceof Cannon) {
                throw new IllegalArgumentException("포는 포를 잡지 못합니다");
            }
        }
        if(count != 1) {
            throw new IllegalArgumentException("포는 기물 하나를 건너 뛰어야 합니다");
        }

        return true;
    }

    @Override
    public Piece from(Position position) {
        return new Cannon(position, getTeam());
    }
}
