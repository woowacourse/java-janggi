package janggi.domain.movestrategy.rule;

import janggi.domain.board.Position;

import java.util.ArrayList;
import java.util.List;

public class StraightForwardMoveRule implements MoveRule {

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
        return path;
    }
}
