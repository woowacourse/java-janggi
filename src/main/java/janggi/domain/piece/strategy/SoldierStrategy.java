package janggi.domain.piece.strategy;

import janggi.domain.Path;
import janggi.domain.Position;

import java.util.List;

public class SoldierStrategy implements MoveStrategy {
    private final int direction;

    public SoldierStrategy(int direction) {
        this.direction = direction;
    }

    @Override
    public List<Path> findMovablePaths(Position current) {
        return List.of(
                new Path(List.of(), Position.of(current.row() + direction, current.column())),
                new Path(List.of(), Position.of(current.row(), current.column() + 1)),
                new Path(List.of(), Position.of(current.row(), current.column() - 1))
        );
    }
}
