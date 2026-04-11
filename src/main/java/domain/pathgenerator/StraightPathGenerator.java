package domain.pathgenerator;

import common.exception.JanggiException;
import domain.direction.Direction;
import domain.position.Path;
import domain.position.Position;

import java.util.ArrayList;
import java.util.List;

public class StraightPathGenerator implements PathGenerator {

    @Override
    public Path calculatePath(Position source, Position destination) {
        validateMove(source, destination);
        Direction direction = determineDirection(source, destination);
        return buildPath(source, destination, direction);
    }

    private void validateMove(Position source, Position destination) {
        if (source.equals(destination)) {
            throw new JanggiException("이동할 수 있는 직선/대각선 경로가 아닙니다.");
        }

        int rowDifference = Math.abs(destination.row() - source.row());
        int columnDifference = Math.abs(destination.column() - source.column());

        boolean isOrthogonal = source.row() == destination.row() || source.column() == destination.column();
        boolean isDiagonal = rowDifference == columnDifference;

        if (!isOrthogonal && !isDiagonal) {
            throw new JanggiException("이동할 수 있는 직선/대각선 경로가 아닙니다.");
        }
    }

    private Direction determineDirection(Position source, Position destination) {
        int rowDifference = destination.row() - source.row();
        int columnDifference = destination.column() - source.column();

        if (rowDifference == 0 || columnDifference == 0) {
            return Direction.fromStraight(rowDifference, columnDifference);
        }
        return Direction.fromDiagonal(rowDifference, columnDifference);
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
