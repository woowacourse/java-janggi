package janggi.domain.movestrategy.rule;

import janggi.domain.board.Position;

import java.util.List;

public interface MoveRule {
    boolean canMove(Position from, Position to);

    List<Position> findPath(Position from, Position to);
}
