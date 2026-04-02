package domain.pathgenerator;

import common.exception.JanggiException;
import domain.direction.Direction;
import domain.position.Path;
import domain.position.Position;
import java.util.ArrayList;
import java.util.List;

public class NonStraightPathGenerator implements PathGenerator {

    private final List<DirectionPath> paths;

    public NonStraightPathGenerator(List<DirectionPath> paths) {
        this.paths = paths;
    }

    @Override
    public Path calculatePath(Position source, Position destination) {
        for (DirectionPath directionPath : paths) {
            try {
                return buildPath(source, destination, directionPath);
            } catch (JanggiException ignored) {
            }
        }
        throw new JanggiException("기물을 이동할 수 없습니다.");
    }

    private Path buildPath(Position source, Position destination, DirectionPath directionPath) {
        List<Position> waypoints = gatherWaypoints(source, directionPath.directions());

        if (!destination.equals(waypoints.getLast())) {
            throw new JanggiException("유효하지 않은 후보 경로입니다.");
        }

        waypoints.removeLast();
        return new Path(source, destination, waypoints);
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

