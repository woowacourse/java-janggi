package domain.pathgenerator;

import domain.direction.Direction;
import domain.position.Path;
import domain.position.Position;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class NonStraightPathGenerator implements PathGenerator {

    private final List<List<Direction>> paths;

    public NonStraightPathGenerator(List<List<Direction>> paths) {
        this.paths = paths;
    }

    @Override
    public Path calculatePath(Position source, Position destination) {
        return paths.stream()
                .map(directionPath -> tryBuildPath(source, destination, directionPath))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("목적지로 이동할 수 없습니다."));
    }

    private Optional<Path> tryBuildPath(Position source, Position destination, List<Direction> directionPath) {
        try {
            List<Position> waypoints = new ArrayList<>();
            Position current = source;
            for (Direction direction : directionPath) {
                current = direction.calculateNextPosition(current);
                waypoints.add(current);
            }

            if (destination.equals(current)) {
                waypoints.removeLast();
                return Optional.of(new Path(source, destination, waypoints));
            }
        } catch (IllegalArgumentException ignored) {
        }
        return Optional.empty();
    }
}
