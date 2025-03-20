package domain.piece.strategy;

import domain.BoardLocation;
import domain.piece.MoveStrategy;
import java.util.List;

public class ChariotMoveStrategy implements MoveStrategy {

    @Override
    public boolean isMovable(BoardLocation current, BoardLocation destination) {
        int differenceX = current.distanceX(destination);
        int differenceY = current.distanceY(destination);
        return differenceX == 0 || differenceY == 0;
    }

    @Override
    public List<BoardLocation> createAllPath(BoardLocation current, BoardLocation destination) {
        return List.of();
    }
}
