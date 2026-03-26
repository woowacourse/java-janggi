package domain.piece;

import domain.position.Position;

import java.util.Map;

public class ElephantMovingCondition implements MovingCondition {
    @Override
    public boolean canMove(Map<Position, Piece> state, Position startPosition, Position endPosition) {
        return false;
    }
}
