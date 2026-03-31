package janggi.domain.piece.strategy;

import janggi.domain.Path;
import janggi.domain.Paths;
import janggi.domain.position.Direction;
import janggi.domain.position.Position;

import java.util.Optional;
import java.util.stream.Stream;

public class ChoSoldierStrategy implements MoveStrategy {
    @Override
    public Paths findMovablePaths(Position current) {
        return new Paths(Stream.of(
                        current.move(Direction.UP),
                        current.move(Direction.LEFT),
                        current.move(Direction.RIGHT)
                )
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(Path::of)
                .toList());
    }
}
