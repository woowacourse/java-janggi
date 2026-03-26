package domain.piece;

import domain.position.Position;

import java.util.Map;

public interface MovingCondition {
    boolean canMove(Map<Position, Piece> state, Position startPosition, Position endPosition);
}
