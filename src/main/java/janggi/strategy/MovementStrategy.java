package janggi.strategy;

import janggi.board.Position;

public interface MovementStrategy {
    boolean canReachGoal(Position start, Position goal);
}
