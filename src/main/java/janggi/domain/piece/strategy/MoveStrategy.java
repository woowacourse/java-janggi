package janggi.domain.piece.strategy;

import janggi.domain.Paths;
import janggi.domain.position.Position;

public interface MoveStrategy {
    Paths findMovablePaths(Position current);
}
