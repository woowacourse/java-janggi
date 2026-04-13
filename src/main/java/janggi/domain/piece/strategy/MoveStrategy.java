package janggi.domain.piece.strategy;

import janggi.domain.Position;
import janggi.domain.board.BoardChecker;

public interface MoveStrategy {

    void validate(Position source, Position destination, BoardChecker board);
}
