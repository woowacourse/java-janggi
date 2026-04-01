package janggi.domain.movestrategy;

import janggi.domain.board.Position;
import java.util.List;

public class HanSoldierStrategy implements MoveStrategy {

    @Override
    public boolean canMoveByBasicMovingRule(Position from, Position to) {
        if (from.deltaY(to) == 1 && from.isSameY(to)) {
            return true;
        }
        return from.distanceX(to) == 1 && from.isSameX(to);
    }

    @Override
    public List<Position> findPath(Position from, Position to) {
        return List.of(to);
    }
}
