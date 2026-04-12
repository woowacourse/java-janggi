package janggi.domain.piece.strategy;

import janggi.domain.Palaces;
import janggi.domain.Path;
import janggi.domain.Paths;
import janggi.domain.position.Direction;
import janggi.domain.position.Position;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public abstract class SoldierStrategy implements MoveStrategy {
    private final Direction forward;
    private final Palaces palaces;

    SoldierStrategy(Direction forward) {
        this.forward = forward;
        this.palaces = Palaces.of();
    }

    @Override
    public Paths findMovablePaths(Position current) {
        List<Direction> directions = new ArrayList<>(List.of(forward, Direction.LEFT, Direction.RIGHT));

        palaces.diagonalDirectionsAt(current).stream()
                .filter(this::isForwardDiagonal)
                .forEach(directions::add);

        return new Paths(directions.stream()
                .map(current::move)
                .flatMap(Optional::stream)
                .map(Path::of)
                .toList());
    }

    private boolean isForwardDiagonal(Direction direction) {
        if (forward == Direction.UP) {
            return direction.isUp();
        }
        return direction.isDown();
    }
}
