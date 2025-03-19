package domain.piece.strategy;

import domain.BoardLocation;
import domain.piece.MoveStrategy;

public class HorseMoveStrategy implements MoveStrategy {

    @Override
    public boolean isMovable(BoardLocation current, BoardLocation destination) {
        int differenceX = current.distanceX(destination);
        int differenceY = current.distanceY(destination);
        return (differenceX == 1 && differenceY == 2) || (differenceX == 2 && differenceY == 1);
    }
}
