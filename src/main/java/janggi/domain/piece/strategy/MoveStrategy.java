package janggi.domain.piece.strategy;

import janggi.domain.Position;
import janggi.domain.Path;

import java.util.List;

public interface MoveStrategy {
    List<Path> findMovablePaths(Position current);
}
