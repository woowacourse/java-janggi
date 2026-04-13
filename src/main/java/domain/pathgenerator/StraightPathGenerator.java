package domain.pathgenerator;

import domain.board.Board;
import domain.direction.Direction;
import domain.position.Path;
import domain.position.Position;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class StraightPathGenerator implements PathGenerator {

    @Override
    public Optional<Path> calculatePath(Position source, Position destination) {
        if (!isPathPossible(source, destination)) {
            return Optional.empty();
        }
        Direction direction = determineDirection(source, destination);
        if (!direction.isStraight()) {
            return Optional.empty();
        }
        return Optional.of(buildPath(source, destination, direction));
    }

    @Override
    public Set<Position> findCandidateDestinations(Position source) {
        Set<Position> candidateDestinations = new HashSet<>();

        for (int row = Board.MIN_ROW; row <= Board.MAX_ROW; row++) {
            if (row != source.row()) {
                candidateDestinations.add(new Position(row, source.column()));
            }
        }

        for (int column = Board.MIN_COLUMN; column <= Board.MAX_COLUMN; column++) {
            if (column != source.column()) {
                candidateDestinations.add(new Position(source.row(), column));
            }
        }
        return candidateDestinations;
    }

    private boolean isPathPossible(Position source, Position destination) {
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
        return Direction.fromDelta(rowDifference, columnDifference);
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
