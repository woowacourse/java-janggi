package domain.piece.strategy;

import domain.BoardLocation;
import domain.piece.MoveStrategy;

public class CannonMoveStrategy implements MoveStrategy {

    @Override
    public boolean isMovable(BoardLocation current, BoardLocation destination) {
        int differenceX = current.distanceX(destination);
        int differenceY = current.distanceY(destination);
        return differenceX == 0 || differenceY == 0;
    }
}
