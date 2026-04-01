package janggi.domain.movestrategy;

import janggi.domain.board.Position;
import java.util.List;

public class GeneralStrategy implements MoveStrategy {

    @Override
    public boolean canMoveByBasicMovingRule(Position from, Position to) {
        if (from.distanceX(to) > 1) {
            return false;
        }
        if (from.distanceY(to) > 1) {
            return false;
        }

        return from.distanceX(to) + from.distanceY(to) <= 1;
    }

    @Override
    public List<Position> findPath(Position from, Position to) {
        return List.of(to);
    }
}
