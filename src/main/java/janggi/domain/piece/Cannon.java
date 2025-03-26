package janggi.domain.piece;

import janggi.domain.piece.movement.Movement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Cannon extends StraightMovingPiece {


    public Cannon(final Position position, final Team team) {
        super("포", position, team);
    }

    @Override
    protected boolean checkPieceCondition(Map<Position, Piece> pieces, Position positionToMove, Movement direction) {
        Position currentPosition = getPosition();
        int count = 0;
        while(currentPosition.isNotEndPoint() || currentPosition.equals(positionToMove)) {
            currentPosition = currentPosition.plus(direction.getX(), direction.getY());
            if(pieces.get(currentPosition).isNotNone()) {
                count ++;
            }
            if(pieces.get(currentPosition) instanceof Cannon) {
                return false;
            }
        }
        return count == 1;
    }
    @Override
    public Piece from(Position position) {
        return new Cannon(position, getTeam());
    }
}
