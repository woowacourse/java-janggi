package janggi.domain.movestorage;

import janggi.domain.BoardState;
import janggi.domain.Position;

public interface MoveStrategy {
    boolean canMove(Position from, Position to, BoardState boardState);
}
