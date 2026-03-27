package janggi.domain.movestrategy;

import janggi.domain.board.Position;

public class CannonStrategy implements MoveStrategy {

    @Override
    public boolean canMove(Position from, Position to) {
        int preX = from.getX();
        int preY = from.getY();

        int nextX = to.getX();
        int nextY = to.getY();

        return (preX == nextX && preY != nextY) || (preX != nextX && preY == nextY);
    }
}
