package domain.pathgenerator;

import domain.direction.Direction;
import domain.position.Path;
import domain.position.Position;
import java.util.ArrayList;
import java.util.List;

public class StraightPathGenerator implements PathGenerator {

    @Override
    public Path calculatePath(Position source, Position destination) {
        if (!validateMove(source, destination)) {
            throw new IllegalArgumentException("이동 할 수 있는 경로가 아닙니다.");
        }

        Direction direction = determineDirection(source, destination);

        return buildPath(source, destination, direction);
    }

    private boolean validateMove(Position source, Position destination) {
        if (source.equals(destination)) {
            return false;
        }

        return source.row() == destination.row() || source.column() == destination.column();
    }

    private Direction determineDirection(Position source, Position destination) {
        if (source.row() == destination.row()) {
            return getDirectionWhenYSame(source, destination);
        }
        if (source.column() == destination.column()) {
            return getDirectionWhenXSame(source, destination);
        }
        throw new IllegalArgumentException("갈 수 있는 경로가 없습니다.");
    }

    private Direction getDirectionWhenXSame(Position source, Position destination) {
        if (source.row() > destination.row()) {
            return Direction.NORTH;
        }
        return Direction.SOUTH;
    }

    private Direction getDirectionWhenYSame(Position source, Position destination) {
        if (source.column() > destination.column()) {
            return Direction.WEST;
        }
        return Direction.EAST;
    }

    private Path buildPath(Position source, Position destination, Direction direction) {
        List<Position> path = new ArrayList<>();
        Position current = source;

        while (!current.equals(destination)) {
            current = direction.move(current);
            path.add(current);
        }

        path.removeLast();
        return new Path(source, destination, path);
    }
}
