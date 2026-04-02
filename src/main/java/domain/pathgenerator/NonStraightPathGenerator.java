package domain.pathgenerator;

import common.exception.JanggiException;
import domain.direction.Direction;
import domain.position.Path;
import domain.position.Position;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class NonStraightPathGenerator implements PathGenerator {

    private final List<DirectionPath> paths;

    public NonStraightPathGenerator(List<DirectionPath> paths) {
        this.paths = paths;
    }

    @Override
    public Path calculatePath(Position source, Position destination) {
        return paths.stream()
                .map(directionPath -> tryBuildPath(source, destination, directionPath))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .findFirst()
                .orElseThrow(() -> new JanggiException("기물을 이동할 수 없습니다."));
    }

    private Optional<Path> tryBuildPath(Position source, Position destination, DirectionPath directionPath) {
        try {
            List<Position> waypoints = gatherWaypoints(source, directionPath.directions());

            if (destination.equals(waypoints.getLast())) {
                waypoints.removeLast();
                return Optional.of(new Path(source, destination, waypoints));
            }
        } catch (JanggiException ignored) {
            return Optional.empty();
        }
        return Optional.empty();
    }

    private List<Position> gatherWaypoints(Position source, List<Direction> directions) {
        List<Position> waypoints = new ArrayList<>();
        Position current = source;
        for (Direction direction : directions) {
            current = direction.calculateNextPosition(current);
            waypoints.add(current);
        }
        return waypoints;
    }
}
