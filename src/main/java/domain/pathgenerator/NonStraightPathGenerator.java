package domain.pathgenerator;

import static common.exception.ErrorMessage.INVALID_PIECE_MOVEMENT;

import common.exception.JanggiException;
import domain.direction.Direction;
import domain.position.Path;
import domain.position.Position;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class NonStraightPathGenerator implements PathGenerator {

    private final List<List<Direction>> paths;

    public NonStraightPathGenerator(List<List<Direction>> paths) {
        this.paths = paths;
    }

    @Override
    public Path calculatePath(Position source, Position destination) {
        return paths.stream()
                .map(directionPath -> tryBuildPath(source, destination, directionPath))
                .filter(Objects::nonNull)
                .findFirst()
                .orElseThrow(() -> new JanggiException(INVALID_PIECE_MOVEMENT.formatted(source, destination)));
    }

    @Override
    public boolean isPathPossible(Position source, Position destination) {
        return paths.stream()
                .anyMatch(directionPath -> tryBuildPath(source, destination, directionPath) != null);
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
