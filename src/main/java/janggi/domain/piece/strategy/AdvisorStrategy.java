package janggi.domain.piece.strategy;

import janggi.domain.Path;

import janggi.domain.Paths;
import janggi.domain.position.Direction;
import janggi.domain.position.Position;

import java.util.Optional;

public class AdvisorStrategy implements MoveStrategy {
    @Override
    public Paths findMovablePaths(Position current) {
        return new Paths(
                Direction.straight().stream()
                        .map(current::move)
                        .filter(Optional::isPresent)
                        .map(Optional::get)
                        .map(Path::of)
                        .toList()
        );
    }
}
