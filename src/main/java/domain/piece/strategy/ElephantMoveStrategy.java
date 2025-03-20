package domain.piece.strategy;

import domain.BoardLocation;
import domain.BoardVector;
import domain.piece.Diagonal;
import domain.piece.Direction;
import domain.piece.MoveStrategy;
import java.util.ArrayList;
import java.util.List;

public class ElephantMoveStrategy implements MoveStrategy {

    @Override
    public boolean isMovable(BoardLocation current, BoardLocation destination) {
        int differenceX = current.distanceX(destination);
        int differenceY = current.distanceY(destination);
        return (differenceX == 2 && differenceY == 3) || (differenceX == 3 && differenceY == 2);
    }

    @Override
    public List<BoardLocation> createAllPath(BoardLocation current, BoardLocation destination) {
        BoardVector boardVector = destination.minus(current);
        List<BoardLocation> path = new ArrayList<>();
        for (Direction direction : Direction.values()) {
            for (Diagonal diagonal : Diagonal.values()) {
                if (diagonal.notContains(direction)) {
                    continue;
                }
                if (boardVector.equals(new BoardVector(direction.getX() + diagonal.getX() + diagonal.getX(), direction.getY() + diagonal.getY() + diagonal.getY()))) {
                    BoardLocation next = current.move(direction.getX(), direction.getY());
                    BoardLocation nextDiagonal = current.move(direction.getX() + diagonal.getX(), direction.getY() + diagonal.getY());
                    path.add(next);
                    path.add(nextDiagonal);
                }
            }
        }
        return path;
    }
}
