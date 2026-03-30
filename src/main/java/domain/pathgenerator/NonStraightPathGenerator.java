package domain.pathgenerator;

import static common.exception.ErrorMessage.INVALID_PIECE_MOVEMENT;

import common.exception.JanggiException;
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
                .orElseThrow(() -> new JanggiException(INVALID_PIECE_MOVEMENT.formatted(source, destination)));
    }

    private Optional<Path> tryBuildPath(Position source, Position destination, List<Direction> directionPath) {
        try {
            List<Position> waypoints = gatherWaypoints(source, directionPath);

            if (destination.equals(waypoints.getLast())) {
                waypoints.removeLast();
                return Optional.of(new Path(source, destination, waypoints));
            }
        } catch (JanggiException ignored) {
            return Optional.empty();
        }
        return Optional.empty();
    }

    private List<Position> gatherWaypoints(Position source, List<Direction> directionPath) {
        List<Position> waypoints = new ArrayList<>();
        Position current = source;
        for (Direction direction : directionPath) {
            current = direction.calculateNextPosition(current);
            waypoints.add(current);
        }
        return waypoints;
    }
}
