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
        int rowDifference = destination.row() - source.row();
        int columnDifference = destination.column() - source.column();
        return Direction.fromStraight(rowDifference, columnDifference);
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
