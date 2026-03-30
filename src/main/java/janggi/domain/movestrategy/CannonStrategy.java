package janggi.domain.movestrategy;

import janggi.domain.board.Position;

import java.util.ArrayList;
import java.util.List;

public class CannonStrategy implements MoveStrategy {

    @Override
    public boolean canMove(Position from, Position to) {
        return from.isInSameLine(to);
    }

    @Override
    public List<Position> findPath(Position from, Position to) {
        List<Position> path = new ArrayList<>();
        Position nextPosition = from.moveStraight(to);
        while (!nextPosition.equals(to)) {
            path.add(nextPosition);
            nextPosition = nextPosition.moveStraight(to);
        }
        path.add(to);
        return path;
    }
}
