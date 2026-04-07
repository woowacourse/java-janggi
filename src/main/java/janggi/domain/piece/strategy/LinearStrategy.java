package janggi.domain.piece.strategy;

import janggi.domain.Palaces;
import janggi.domain.Path;
import janggi.domain.Paths;
import janggi.domain.position.Direction;
import janggi.domain.position.Position;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class LinearStrategy implements MoveStrategy {
    private final Palaces palaces;

    public LinearStrategy(Palaces palaces) {
        this.palaces = palaces;
    }

    @Override
    public Paths findMovablePaths(Position current) {
        List<Path> paths = new ArrayList<>();

        for (Direction direction : Direction.straight()) {
            paths.addAll(collectPaths(current, direction));
        }

        for (Direction direction : palaces.diagonalDirectionsAt(current)) {
            paths.addAll(collectPalacePaths(current, direction));
        }
        return new Paths(paths);
    }

    private List<Path> collectPaths(Position current, Direction direction) {
        List<Path> paths = new ArrayList<>();
        List<Position> waypoints = new ArrayList<>();
        Optional<Position> next = current.move(direction);

        while (next.isPresent()) {
            Position destination = next.get();
            paths.add(Path.of(List.copyOf(waypoints), destination));
            waypoints.add(destination);
            next = destination.move(direction);
        }
        return paths;
    }

    private List<Path> collectPalacePaths(Position current, Direction direction) {
        List<Path> paths = new ArrayList<>();
        List<Position> waypoints = new ArrayList<>();
        Optional<Position> next = current.move(direction);

        while (next.isPresent() && palaces.containsAny(next.get())) {
            Position destination = next.get();
            paths.add(Path.of(List.copyOf(waypoints), destination));
            waypoints.add(destination);
            next = destination.move(direction);
        }
        return paths;
    }
}

