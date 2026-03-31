package janggi.domain.movestrategy;

import static java.lang.Math.abs;

import janggi.domain.board.Position;
import java.util.List;

public class GeneralStrategy implements MoveStrategy {

    @Override
    public boolean canMoveByBasicMovingRule(Position from, Position to) {
        int preX = from.x();
        int preY = from.y();

        int nextX = to.x();
        int nextY = to.y();

        if (abs(preX - nextX) > 1) {
            return false;
        }
        if (abs(preY - nextY) > 1) {
            return false;
        }

        return abs(preX - nextX) + abs(preY - nextY) <= 1;
    }

    @Override
    public List<Position> findPath(Position from, Position to) {
        return List.of(to);
    }
}
