package janggi.domain.piece.strategy;

import janggi.domain.Path;
import janggi.domain.Position;

import java.util.List;

public class ElephantStrategy implements MoveStrategy{
    @Override
    public List<Path> findMovablePaths(Position current) {
        return List.of(
                new Path(
                        List.of(Position.of(current.row() + 1, current.column()),
                                Position.of(current.row() + 2, current.column() + 1)),
                        Position.of(current.row() + 3, current.column() + 2)),
                new Path(
                        List.of(Position.of(current.row(), current.column() + 1),
                                Position.of(current.row() + 1, current.column() + 2)),
                        Position.of(current.row() + 2, current.column() + 3)),
                new Path(
                        List.of(Position.of(current.row(), current.column() + 1),
                                Position.of(current.row() - 1, current.column() + 2)),
                        Position.of(current.row() - 2, current.column() + 3)),
                new Path(
                        List.of(Position.of(current.row() - 1, current.column()),
                                Position.of(current.row() - 2, current.column() + 1)),
                        Position.of(current.row() - 3, current.column() + 2)),
                new Path(
                        List.of(Position.of(current.row() + 1, current.column()),
                                Position.of(current.row() + 2, current.column() - 1)),
                        Position.of(current.row() + 3, current.column() - 2)),
                new Path(
                        List.of(
                                Position.of(current.row(), current.column() - 1),
                                Position.of(current.row() + 1, current.column() - 2)),
                        Position.of(current.row() + 2, current.column() - 3)),
                new Path(
                        List.of(Position.of(current.row(), current.column() - 1),
                                Position.of(current.row() - 1, current.column() - 2)),
                        Position.of(current.row() - 2, current.column() - 3)),
                new Path(
                        List.of(Position.of(current.row() - 1, current.column()),
                                Position.of(current.row() - 2, current.column() - 1)),
                        Position.of(current.row() - 3, current.column() - 2)));
    }
}
