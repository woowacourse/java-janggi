package janggi.domain.movestrategy.rule;

import janggi.domain.board.Position;

import java.util.List;

public class StraightOneStepMoveRule implements MoveRule {

    @Override
    public boolean canMove(Position from, Position to) {
        return from.calculateManhattanDistance(to) == 1;
    }

    @Override
    public List<Position> findPath(Position from, Position to) {
        return List.of();
    }
}
