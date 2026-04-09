package domain.pathgenerator;

import domain.direction.Direction;
import domain.gungsung.Gungsung;
import domain.position.Path;
import domain.position.Position;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class GungsungDiagonalPathGenerator implements PathGenerator {

    private static final Gungsung GUNGSUNG = new Gungsung();

    @Override
    public Optional<Path> calculatePath(Position source, Position destination) {
        if (!isPathPossible(source, destination)) {
            return Optional.empty();
        }
        Direction direction = determineDirection(source, destination);
        if (!direction.isDiagonal()) {
            return Optional.empty();
        }
        return Optional.of(buildPath(source, destination, direction));
    }

    @Override
    public Set<Position> findCandidateDestinations(Position source) {
        Set<Position> candidateDestinations = new HashSet<>();
        for (Position destination : GUNGSUNG.getAllPositions()) {
            if (GUNGSUNG.isDiagonal(source, destination)) {
                candidateDestinations.add(destination);
            }
        }
        return candidateDestinations;
    }

    private boolean isPathPossible(Position source, Position destination) {
        return GUNGSUNG.isDiagonal(source, destination);
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
