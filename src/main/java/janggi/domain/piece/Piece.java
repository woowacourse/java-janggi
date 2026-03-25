package janggi.domain.piece;

import janggi.domain.BoardInterface;
import janggi.domain.Position;

public interface Piece {
    boolean isMovable(Position start, Position end, BoardInterface boardInterface);
    boolean isPo();
}
