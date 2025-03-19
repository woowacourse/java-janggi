package strategy;

import piece.Position;
import piece.Route;

public interface MoveStrategy {
    Route getLegalRoute(Position startPosition, Position endPosition);
    boolean isAbleToMove();
}
