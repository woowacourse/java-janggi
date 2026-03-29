package janggi.domain.movestrategy;

import janggi.domain.board.Position;

public class HanSoldierStrategy implements MoveStrategy {

    @Override
    public boolean canMove(Position from, Position to) {
        int preX = from.getX();
        int preY = from.getY();

        int nextY = to.getY();
        int nextX = to.getX();

        if (nextY - preY == 1 && preX == nextX) {
            return true;
        }
        return (Math.abs(nextX - preX) == 1) && (nextY == preY);
    }
}
