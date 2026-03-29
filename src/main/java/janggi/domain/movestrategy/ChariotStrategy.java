package janggi.domain.movestrategy;

import janggi.domain.board.Position;

public class ChariotStrategy implements MoveStrategy{

    @Override
    public boolean canMoveByBasicMovingRule(Position from, Position to) {
        int preX = from.getX();
        int preY = from.getY();

        int nextX = to.getX();
        int nextY = to.getY();

        return (preX == nextX && preY != nextY) || (preX != nextX && preY == nextY);
    }
}
