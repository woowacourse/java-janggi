package domain.piece.strategy;

import domain.position.Position;
import java.util.List;

public class SlidingMoveStrategy implements MoveStrategy {
    @Override
    public List<Position> findMovablePath(Position start, Position destination) {
        if (start.isSameRow(destination.getRow())) {
            return start.getSameRowPositionsToDestination(destination);
        }
        if (start.isSameColumn(destination.getColumn())) {
            return start.getSameColumnPositionsToDestination(destination);
        }
        throw new IllegalArgumentException("[ERROR] 잘못된 좌표입니다. 다시 입력하세요.");
    }
}
