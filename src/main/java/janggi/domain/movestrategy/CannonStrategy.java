package janggi.domain.movestrategy;

import janggi.domain.board.Position;
import java.util.ArrayList;
import java.util.List;

public class CannonStrategy implements MoveStrategy {

    @Override
    public boolean canMoveByBasicMovingRule(Position from, Position to) {
        return (from.isSameColumn(to) && !from.isSameRow(to))
                || (!from.isSameColumn(to) && from.isSameRow(to));
    }

    @Override
    public List<Position> findPath(Position from, Position to) {
        List<Position> path = new ArrayList<>();

        int currentX = from.x();
        int currentY = from.y();

        int stepX = Integer.compare(to.x(), from.x());
        int stepY = Integer.compare(to.y(), from.y());

        while (currentX != to.x() || currentY != to.y()) {
            currentX += stepX;
            currentY += stepY;
            path.add(new Position(currentX, currentY));
        }
        return path;
    }
}
