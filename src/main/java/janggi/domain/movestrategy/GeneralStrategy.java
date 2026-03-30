package janggi.domain.movestrategy;

import janggi.domain.board.Position;

import java.util.List;

public class GeneralStrategy implements MoveStrategy{

    @Override
    public boolean canMove(Position from, Position to) {
        return from.isApartFrom(to) == 1;
    }

    @Override
    public List<Position> findPath(Position from, Position to) {
        return List.of(to);
    }
}
