package domain.piece.strategy;

import domain.board.BoardChecker;
import domain.board.Position;

public interface MoveStrategy {
    void validateMove(Position from, Position to, BoardChecker checker);
}
