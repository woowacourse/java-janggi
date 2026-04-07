package domain.pathgenerator;

import domain.direction.Direction;
import domain.position.Path;
import domain.position.Position;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class NonStraightPathGenerator implements PathGenerator {

    private static final String INVALID_MOVEMENT = "기물의 이동규칙에 어긋납니다.";
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
}
