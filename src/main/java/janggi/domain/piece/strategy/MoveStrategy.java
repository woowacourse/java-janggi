package janggi.domain.piece.strategy;

import janggi.domain.Position;
import java.util.List;

public interface MoveStrategy {

    List<Position> findPath(Position from, Position to);
}
