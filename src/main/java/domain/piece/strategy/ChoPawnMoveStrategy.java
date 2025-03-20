package domain.piece.strategy;

import domain.BoardLocation;
import domain.piece.MoveStrategy;
import java.util.List;

public class ChoPawnMoveStrategy implements MoveStrategy {

    @Override
    public boolean isMovable(BoardLocation current, BoardLocation destination) {
        int differenceX = current.distanceX(destination);
        int differenceY = current.distanceY(destination);
        boolean a = differenceX == 0 || differenceY == 0;
        boolean b = differenceX == 1 || differenceY == 1;
        boolean c = destination.isDown(current);
        return a && b && !c;
    }

    @Override
    public List<BoardLocation> createAllPath(BoardLocation current, BoardLocation destination) {
        return List.of(destination);
    }
}
