package janggi.domain.piece;

import janggi.domain.BoardInterface;
import janggi.domain.Position;

import java.util.List;

public interface Piece {
    List<Position> findRoute(Position start, Position end);
    boolean isMovable(List<Position> path, BoardInterface boardInterface);
    boolean isPo();
}
