package janggi.domain.piece.strategy;

import janggi.domain.Path;
import janggi.domain.Paths;
import janggi.domain.WayPoints;
import janggi.domain.position.Direction;
import janggi.domain.position.Position;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class GeneralStrategy implements MoveStrategy {
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
