package janggi.domain.movestrategy;

import janggi.domain.board.Position;

public interface MoveStrategy {
    boolean canMoveByBasicMovingRule(Position from, Position to);
}

