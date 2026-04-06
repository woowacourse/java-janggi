package janggi.domain.movestorage;

import janggi.domain.BoardView;
import janggi.domain.Position;

public interface MoveStorage {
    boolean canMove(Position from, Position to, BoardView boardState);
}
