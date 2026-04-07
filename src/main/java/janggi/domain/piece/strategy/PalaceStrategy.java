package janggi.domain.piece.strategy;

import janggi.domain.Palace;
import janggi.domain.Path;
import janggi.domain.Paths;
import janggi.domain.position.Direction;
import janggi.domain.position.Position;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PalaceStrategy implements MoveStrategy {
    private final Palace palace;

    PalaceStrategy(Palace palace) {
        this.palace = palace;
    }

    @Override
    public Paths findMovablePaths(Position current) {
        List<Direction> directions = new ArrayList<>(Direction.straight());

        directions.addAll(palace.diagonalDirectionsAt(current));

        return new Paths(
                directions.stream()
                        .map(current::move)
                        .flatMap(Optional::stream)
                        .filter(palace::contains)
                        .map(Path::of)
                        .toList()
        );
    }
}
