package janggi.domain.piece.strategy;

import janggi.domain.Path;
import janggi.domain.JanggiPosition;

import java.util.List;

public interface MoveStrategy {
    List<Path> findMovablePaths(JanggiPosition current);
}
