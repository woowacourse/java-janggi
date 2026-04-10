package janggi.domain.piece.strategy;

import janggi.domain.board.Position;
import java.util.List;

public interface MoveStrategy {

    List<Position> findPath(Position source, Position destination);
}
