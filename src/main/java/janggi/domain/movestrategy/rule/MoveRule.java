package janggi.domain.movestrategy.rule;

import janggi.domain.board.BoardState;
import janggi.domain.position.Position;

public interface MoveRule {
    boolean isValid(Position from, Position to, BoardState boardState);
}
