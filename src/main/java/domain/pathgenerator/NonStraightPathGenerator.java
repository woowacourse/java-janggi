package domain.pathgenerator;

import domain.direction.Direction;
import domain.position.Path;
import domain.position.Position;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

public class NonStraightPathGenerator implements PathGenerator {

    private final List<List<Direction>> paths;

    public NonStraightPathGenerator(List<List<Direction>> paths) {
        this.paths = paths;
    }

    @Override
    public Optional<Path> calculatePath(Position source, Position destination) {
        return paths.stream()
                .map(directionPath -> tryBuildPath(source, destination, directionPath))
                .filter(Objects::nonNull)
                .findFirst();
    }

    @Override
    public Set<Position> findCandidateDestinations(Position source) {
        Set<Position> candidateDestinations = new HashSet<>();
        for (List<Direction> directionPath : paths) {
            findDestination(source, directionPath)
                    .ifPresent(candidateDestinations::add);
        }
        return candidateDestinations;
    }

    private Path tryBuildPath(Position source, Position destination, List<Direction> directionPath) {
        List<Position> waypoints = new ArrayList<>();
        Position current = source;

        for (Direction direction : directionPath) {
            if (!direction.canCalculateNextPosition(current)) {
                return null;
            }
            current = direction.calculateNextPosition(current);
            waypoints.add(current);
        }

        if (!destination.equals(current)) {
            return null;
        }

        waypoints.removeLast();
        return new Path(source, destination, waypoints);
    }

    private Optional<Position> findDestination(Position source, List<Direction> directionPath) {
        Position current = source;
        for (Direction direction : directionPath) {
            if (!direction.canCalculateNextPosition(current)) {
                return Optional.empty();
            }
            current = direction.calculateNextPosition(current);
        }
        return Optional.of(current);
    }
}
