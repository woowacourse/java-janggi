package janggi.domain.piece.strategy;

import janggi.domain.Path;
import janggi.domain.Position;

import java.util.List;

public class AdvisorStrategy implements MoveStrategy{
    @Override
    public List<Path> findMovablePaths(Position current) {
        return List.of(
                new Path(List.of(), Position.of(current.row() + 1, current.column())),
                new Path(List.of(), Position.of(current.row() - 1, current.column())),
                new Path(List.of(), Position.of(current.row(), current.column() + 1)),
                new Path(List.of(), Position.of(current.row(), current.column() - 1))
        );
    }
}
