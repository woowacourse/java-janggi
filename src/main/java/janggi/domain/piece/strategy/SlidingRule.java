package janggi.domain.piece.strategy;

import janggi.domain.Position;
import janggi.exception.ExceptionMessage;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;

public abstract class SlidingRule extends BaseMoveRule {

    protected List<Position> findPath(Position source, DirectionInformation directionInformation) {
        if (directionInformation.isHorizontal()) {
            return createPath(source, directionInformation.colDistance(), Position::moveCol);
        }
        if (directionInformation.isVertical()) {
            return createPath(source, directionInformation.rowDistance(), Position::moveRow);
        }
        throw new IllegalArgumentException(ExceptionMessage.ONLY_STRAIGHT_MOVE_ALLOWED.getMessage());
    }

    private List<Position> createPath(Position source, int difference, BiFunction<Position, Integer, Position> move) {
        List<Position> path = new ArrayList<>();
        int direction = Integer.signum(difference);
        int distance = Math.abs(difference);

        for (int i = 0; i < distance; i++) {
            source = move.apply(source, direction);
            path.add(source);
        }
        return path;
    }
}
