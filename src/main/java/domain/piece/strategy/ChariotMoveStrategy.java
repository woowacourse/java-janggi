package domain.piece.strategy;

import domain.BoardLocation;
import domain.BoardVector;
import domain.piece.MoveStrategy;
import java.util.ArrayList;
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
        List<BoardLocation> path = new ArrayList<>();
        path.add(destination);
        BoardVector boardVector = destination.minus(current);

        if (boardVector.isDxZero()) {
            int dy = boardVector.dy();
            for (int i = 1; i < dy; i++) {
                path.add(current.moveY(i));
            }
            return path;
        }

        int dx = boardVector.dx();
        for (int i = 1; i < dx; i++) {
            path.add(current.moveX(i));
        }
        return path;
    }
}
