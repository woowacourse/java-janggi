package domain.piece.strategy;

import domain.board.PathChecker;
import domain.board.Position;

public interface MoveStrategy {
    void move(Position from, Position to, PathChecker checker);
}
