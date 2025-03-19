package strategy;

import piece.Position;
import piece.Route;

public class SangMoveStrategy implements MoveStrategy {
    @Override
    public Route getLegalRoute(Position startPosition, Position endPosition) {
        return null;
    }

    @Override
    public boolean isAbleToMove() {
        return false;
    }

}
