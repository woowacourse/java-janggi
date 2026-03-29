package janggi.domain.movestrategy;

import janggi.domain.board.Position;

public class ChoSoldierStrategy implements MoveStrategy {

    @Override
    public boolean canMoveByBasicMovingRule(Position from, Position to) {
        int preX = from.getX();
        int preY = from.getY();

        int nextY = to.getY();
        int nextX = to.getX();

        if (preY - nextY == 1 && preX == nextX) {
            return true;
        }
        return (Math.abs(nextX - preX) == 1) && (nextY == preY);
    }
}
