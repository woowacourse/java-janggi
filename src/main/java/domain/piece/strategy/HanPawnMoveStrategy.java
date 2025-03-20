package domain.piece.strategy;

import domain.BoardLocation;
import domain.piece.MoveStrategy;
import java.util.List;

public class HanPawnMoveStrategy implements MoveStrategy {

    @Override
    public boolean isMovable(BoardLocation current, BoardLocation destination) {
        int differenceX = current.distanceX(destination);
        int differenceY = current.distanceY(destination);
        boolean isOrthogonalMove = differenceX == 0 || differenceY == 0;
        boolean isOneStepMove = differenceX == 1 || differenceY == 1;
        boolean isMovingUp = destination.isUp(current);
        return isOrthogonalMove && isOneStepMove && !isMovingUp;
    }

    @Override
    public List<BoardLocation> createAllPath(BoardLocation current, BoardLocation destination) {
        return List.of();
    }
}
