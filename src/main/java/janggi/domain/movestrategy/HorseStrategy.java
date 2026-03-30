package janggi.domain.movestrategy;

import janggi.domain.board.Position;

import java.util.List;

public class HorseStrategy implements MoveStrategy{

    @Override
    public boolean canMove(Position from, Position to) {
        return from.isMatchDistance(to, 1, 2);
    }

    @Override
    public List<Position> findPath(Position from, Position to) {
        Position mid = from.moveStraight(to);
        return List.of(mid, to);
    }
}
