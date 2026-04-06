package domain.pathgenerator;

import common.JanggiException;
import domain.direction.Direction;
import domain.position.Path;
import domain.position.Position;
import java.util.ArrayList;
import java.util.List;

public class StraightPathGenerator implements PathGenerator {

    private static final String INVALID_STRAIGHT_PATH = "직선 경로가 아닙니다.";

    @Override
    public Path calculatePath(Position source, Position destination) {
        if (!isPathPossible(source, destination)) {
            throw new JanggiException(INVALID_STRAIGHT_PATH);
        }
        Direction direction = determineDirection(source, destination);
        if (!direction.isStraight()) {
            throw new JanggiException(INVALID_STRAIGHT_PATH);
        }
        return buildPath(source, destination, direction);
    }

    @Override
    public boolean isPathPossible(Position source, Position destination) {
        if (source.equals(destination)) {
            return false;
        }
        return isHorizontal(source, destination) || isVertical(source, destination);
    }

    private boolean isHorizontal(Position source, Position destination) {
        return source.row() == destination.row();
    }

    private boolean isVertical(Position source, Position destination) {
        return source.column() == destination.column();
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
