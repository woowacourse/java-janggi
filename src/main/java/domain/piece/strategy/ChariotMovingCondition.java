package domain.piece.strategy;

import domain.piece.Piece;
import domain.position.Position;

import java.util.Map;

public class ChariotMovingCondition implements MovingCondition {

    @Override
    public boolean canMove(Map<Position, Piece> state, Position startPosition, Position endPosition) {
        Directions directions = Directions.between(startPosition, endPosition);
        if (!directions.isStraightDirection()) {
            return false;
        }
        return hasValidChariotPath(state, endPosition, new LinePath(startPosition, directions));
    }

    private boolean hasValidChariotPath(Map<Position, Piece> state, Position endPosition, LinePath path) {
        while (path.hasNext()) {
            if (!path.moveForward()) {
                return false;
            }
            if (path.isAt(endPosition)) {
                return true;
            }
            if (path.isBlockedBy(state)) {
                return false;
            }
        }
        return true;
    }
}
