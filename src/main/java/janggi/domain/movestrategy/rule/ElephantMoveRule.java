package janggi.domain.movestrategy.rule;

import janggi.domain.board.Position;

import java.util.List;

public class ElephantMoveRule implements MoveRule {
    @Override
    public boolean canMove(Position from, Position to) {
        return from.isMatchDistance(to, 2, 3);
    }

    @Override
    public List<Position> findPath(Position from, Position to) {
        Position first = from.moveStraight(to);
        Position second = first.moveDiagonal(to);
        return List.of(first, second);
    }
}
