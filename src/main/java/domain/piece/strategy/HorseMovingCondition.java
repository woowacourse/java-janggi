package domain.piece.strategy;

import domain.piece.Piece;
import domain.position.Position;

import java.util.Map;

public class HorseMovingCondition implements MovingCondition {
    @Override
    public boolean canMove(Map<Position, Piece> state, Position startPosition, Position endPosition) {
        return false;
    }
}
