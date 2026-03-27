package janggi.domain.movestrategy;

import janggi.domain.board.Position;

public interface MoveStrategy {
    boolean canMove(Position from, Position to);
}

