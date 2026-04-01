package janggi.domain.movestrategy;

import janggi.domain.board.Position;
import java.util.ArrayList;
import java.util.List;

public class CannonStrategy implements MoveStrategy {

    @Override
    public boolean canMoveByBasicMovingRule(Position from, Position to) {
        return (from.isSameY(to) && !from.isSameX(to))
                || (!from.isSameY(to) && from.isSameX(to));
    }

    @Override
    public List<Position> findPath(Position from, Position to) {
        List<Position> path = new ArrayList<>();
        int stepX = Integer.compare(to.x(), from.x());
        int stepY = Integer.compare(to.y(), from.y());
        Position currentPosition = from;

        while (!currentPosition.equals(to)) {
            currentPosition = currentPosition.moveBy(stepX, stepY);
            path.add(currentPosition);
        }
        return path;
    }
}
