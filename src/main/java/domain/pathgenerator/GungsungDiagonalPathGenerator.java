package domain.pathgenerator;

import domain.direction.Direction;
import domain.position.Path;
import domain.position.Position;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class GungsungDiagonalPathGenerator implements PathGenerator {

    private static final List<List<Position>> CHO_GUNGSUNGS = List.of(
            List.of(new Position(7, 3), new Position(7, 4), new Position(7, 5)),
            List.of(new Position(8, 3), new Position(8, 4), new Position(8, 5)),
            List.of(new Position(9, 3), new Position(9, 4), new Position(9, 5))
    );

    private static final List<List<Position>> HAN_GUNGSUNGS = List.of(
            List.of(new Position(0, 3), new Position(0, 4), new Position(0, 5)),
            List.of(new Position(1, 3), new Position(1, 4), new Position(1, 5)),
            List.of(new Position(2, 3), new Position(2, 4), new Position(2, 5))
    );

    private static final List<List<List<Integer>>> MOVEABLE_INDEX_PAIR = List.of(
            List.of(List.of(0, 0), List.of(1, 1)),
            List.of(List.of(0, 0), List.of(2, 2)),

            List.of(List.of(0, 2), List.of(1, 1)),
            List.of(List.of(0, 2), List.of(2, 0)),

            List.of(List.of(1, 1), List.of(0, 0)),
            List.of(List.of(1, 1), List.of(0, 2)),
            List.of(List.of(1, 1), List.of(2, 0)),
            List.of(List.of(1, 1), List.of(2, 2)),

            List.of(List.of(2, 0), List.of(0, 2)),
            List.of(List.of(2, 0), List.of(1, 1)),

            List.of(List.of(2, 2), List.of(0, 0)),
            List.of(List.of(2, 2), List.of(1, 1))
    );

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
        for (List<Position> gungsungLine : CHO_GUNGSUNGS) {
            for (Position destination : gungsungLine) {
                if (isPathPossible(source, destination)) {
                    candidateDestinations.add(destination);
                }
            }
        }
        for (List<Position> gungsungLine : HAN_GUNGSUNGS) {
            for (Position destination : gungsungLine) {
                if (isPathPossible(source, destination)) {
                    candidateDestinations.add(destination);
                }
            }
        }

        return candidateDestinations;
    }

    private boolean isPathPossible(Position source, Position destination) {
        for (List<List<Integer>> length1IndexPair : MOVEABLE_INDEX_PAIR) {
            List<Integer> targetSourceIndex = length1IndexPair.getFirst();
            List<Integer> targetDestinationIndex = length1IndexPair.get(1);

            Position targetSource = CHO_GUNGSUNGS.get(targetSourceIndex.getFirst())
                    .get(targetSourceIndex.get(1));
            Position targetDestination = CHO_GUNGSUNGS.get(targetDestinationIndex.getFirst())
                    .get(targetDestinationIndex.get(1));

            if (source.equals(targetSource) && destination.equals(targetDestination)) {
                return true;
            }
        }

        for (List<List<Integer>> length1IndexPair : MOVEABLE_INDEX_PAIR) {
            List<Integer> targetSourceIndex = length1IndexPair.getFirst();
            List<Integer> targetDestinationIndex = length1IndexPair.get(1);

            Position targetSource = HAN_GUNGSUNGS.get(targetSourceIndex.getFirst())
                    .get(targetSourceIndex.get(1));
            Position targetDestination = HAN_GUNGSUNGS.get(targetDestinationIndex.getFirst())
                    .get(targetDestinationIndex.get(1));

            if (source.equals(targetSource) && destination.equals(targetDestination)) {
                return true;
            }
        }

        return false;
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
