package janggi.domain.piece;

import janggi.domain.BoardInterface;
import janggi.domain.Position;

import java.util.List;

public interface Piece {
    boolean isMovable(Position start, Position end, BoardInterface boardInterface);
    boolean isPo();
}
