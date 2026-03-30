package janggi.domain.movestrategy;

import janggi.domain.board.Position;

import java.util.ArrayList;
import java.util.List;

public class HorseStrategy implements MoveStrategy{

    @Override
    public boolean canMove(Position from, Position to) {
        return from.isMatchDistance(to, 1, 2);
    }

    @Override
    public List<Position> findPath(Position from, Position to) {
        List<Position> path = new ArrayList<>();

        int preX = from.getX();
        int preY = from.getY();

        int nextX = to.getX();
        int nextY = to.getY();

        if (Math.abs(preX - nextX) == 2) {
            if (nextX > preX) {
                path.add(new Position(preX + 1, preY));
                if (nextY > preY) {
                    path.add(new Position(preX + 2, preY + 1));
                    return path;
                }
                path.add(new Position(preX + 2, preY - 1));
                return path;
            }
            path.add(new Position(preX - 1, preY));
            if (nextY > preY) {
                path.add(new Position(preX - 2, preY + 1));
                return path;
            }
            path.add(new Position(preX - 2, preY - 1));
            return path;
        }
        if (nextY > preY) {
            path.add(new Position(preX, preY + 1));
            if (nextX > preX) {
                path.add(new Position(preX + 1, preY + 2));
                return path;
            }
            path.add(new Position(preX - 1, preY + 2));
            return path;
        }
        path.add(new Position(preX, preY - 1));
        if (nextX > preX) {
            path.add(new Position(preX + 1, preY - 2));
            return path;
        }
        path.add(new Position(preX - 1, preY - 2));
        return path;
    }
}
