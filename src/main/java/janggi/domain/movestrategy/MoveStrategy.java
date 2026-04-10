package janggi.domain.movestrategy;

import janggi.domain.board.BoardState;
import janggi.domain.position.Position;

public interface MoveStrategy {
    boolean canMove(Position from, Position to, BoardState boardState);
}
