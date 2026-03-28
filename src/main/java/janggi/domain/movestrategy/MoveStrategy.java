package janggi.domain.movestrategy;

import janggi.domain.board.Position;

import java.util.List;

public interface MoveStrategy {
    boolean canMove(Position from, Position to);

    List<Position> findPath(Position from, Position to);
}

