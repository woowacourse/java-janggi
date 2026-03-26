package janggi.domain.piece.strategy;

import janggi.domain.Path;
import janggi.domain.Position;

import java.util.List;

public class SoldierStrategy implements MoveStrategy {

    @Override
    public List<Path> findMovablePaths(Position current) {
        return List.of();
    }
}
