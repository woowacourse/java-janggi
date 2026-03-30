package janggi.domain.piece.strategy;

import janggi.domain.Path;
import janggi.domain.Paths;
import janggi.domain.position.Direction;
import janggi.domain.position.Position;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ChariotStrategy implements MoveStrategy {
    @Override
    public Paths findMovablePaths(Position current) {
        List<Path> paths = new ArrayList<>();
        for (Direction direction : Direction.straight()) {
            paths.addAll(collectPaths(current, direction));
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
}
