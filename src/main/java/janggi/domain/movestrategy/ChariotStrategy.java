package janggi.domain.movestrategy;

import janggi.domain.board.Position;
import java.util.ArrayList;
import java.util.List;

public class ChariotStrategy implements MoveStrategy{

    @Override
    public boolean canMoveByBasicMovingRule(Position from, Position to) {
        int preX = from.getX();
        int preY = from.getY();

        int nextX = to.getX();
        int nextY = to.getY();

        return (preX == nextX && preY != nextY) || (preX != nextX && preY == nextY);
    }

    @Override
    public List<Position> findPath(Position from, Position to) {
        List<Position> path = new ArrayList<>();

        int currentX = from.getX();
        int currentY = from.getY();

        int stepX = Integer.compare(to.getX(), from.getX());
        int stepY = Integer.compare(to.getY(), from.getY());

        while (currentX != to.getX() || currentY != to.getY()) {
            currentX += stepX;
            currentY += stepY;
            path.add(new Position(currentX, currentY));
        }
        return path;
    }
}
