package janggi.domain.piece.strategy;

import janggi.domain.Path;
import janggi.domain.position.Position;

import java.util.List;

public interface MoveStrategy {
    List<Path> findMovablePaths(Position current);
}
