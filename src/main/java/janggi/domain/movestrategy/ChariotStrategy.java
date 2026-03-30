package janggi.domain.movestrategy;

import janggi.domain.board.Position;

import java.util.ArrayList;
import java.util.List;

public class ChariotStrategy implements MoveStrategy{

    @Override
    public boolean canMove(Position from, Position to) {
        return from.isInSameLine(to);
    }

    @Override
    public List<Position> findPath(Position from, Position to) {
        List<Position> path = new ArrayList<>();
        int preX = from.getX();
        int preY = from.getY();

        int nextX = to.getX();
        int nextY = to.getY();

        // 시작점이 포함되는 오류가 있다
        if (preX == nextX) {
            if (nextY > preY) {
                for (int y = preY + 1; y <= nextY; y++) {
                    path.add(new Position(preX, y));
                }
                return path;
            }
            for (int y = nextY; y < preY; y++) {
                path.add(new Position(preX, y));
            }
            return path;
        }

        if (nextX > preX) {
            for (int x = preX + 1; x <= nextX; x++) {
                path.add(new Position(x, preY));
            }
            return path;
        }
        for (int x = nextX; x <= preX; x++) {
            path.add(new Position(x, preY));
        }
        return path;
    }
}
