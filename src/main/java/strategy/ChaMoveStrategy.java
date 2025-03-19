package strategy;

import piece.Position;
import piece.Route;

public class ChaMoveStrategy implements MoveStrategy{
    @Override
    public Route getLegalRoute(Position startPosition, Position endPosition) {
        return null;
    }

    @Override
    public boolean isAbleToMove() {
        return false;
    }

}
