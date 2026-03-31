package janggi.domain.movestrategy;

import janggi.domain.board.Position;
import java.util.List;

public class HanSoldierStrategy implements MoveStrategy {

    @Override
    public boolean canMoveByBasicMovingRule(Position from, Position to) {
        int preX = from.x();
        int preY = from.y();

        int nextY = to.y();
        int nextX = to.x();

        if (nextY - preY == 1 && preX == nextX) {
            return true;
        }
        return (Math.abs(nextX - preX) == 1) && (nextY == preY);
    }

    @Override
    public List<Position> findPath(Position from, Position to) {
        return List.of(to);
    }
}
