package domain.pathgenerator;

import common.exception.JanggiException;
import domain.direction.Direction;
import domain.position.Path;
import domain.position.Position;
import java.util.ArrayList;
import java.util.List;

public class DiagonalStraightGenerator implements PathGenerator {

    @Override
    public Path calculatePath(Position source, Position destination) {
        Direction direction = determineDiagonalDirection(source, destination);
        return buildPath(source, destination, direction);
    }

    private Direction determineDiagonalDirection(Position source, Position destination) {
        int rowDifference = destination.row() - source.row();
        int columnDifference = destination.column() - source.column();

        if (rowDifference < 0 && columnDifference > 0) {
            return Direction.NORTH_EAST;
        }
        if (rowDifference < 0) {
            return Direction.NORTH_WEST;
        }
        if (columnDifference > 0) {
            return Direction.SOUTH_EAST;
        }
        return Direction.SOUTH_WEST;
    }

    private Path buildPath(Position source, Position destination, Direction direction) {
        List<Position> path = new ArrayList<>();
        Position current = source;

        while (!current.equals(destination)) {
            current = direction.calculateNextPosition(current);
            path.add(current);
        }

        path.removeLast();
        return new Path(source, destination, path);
    }
}
