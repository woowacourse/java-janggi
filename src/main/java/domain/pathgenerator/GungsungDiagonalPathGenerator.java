package domain.pathgenerator;

import common.JanggiException;
import domain.direction.Direction;
import domain.position.Path;
import domain.position.Position;
import java.util.ArrayList;
import java.util.List;

public class GungsungDiagonalPathGenerator implements PathGenerator {

    private static final List<List<Position>> choGungsungs = List.of(
            List.of(new Position(7, 3), new Position(7, 4), new Position(7, 5)),
            List.of(new Position(8, 3), new Position(8, 4), new Position(8, 5)),
            List.of(new Position(9, 3), new Position(9, 4), new Position(9, 5))
    );

    private static final List<List<Position>> hanGungsungs = List.of(
            List.of(new Position(0, 3), new Position(0, 4), new Position(0, 5)),
            List.of(new Position(1, 3), new Position(1, 4), new Position(1, 5)),
            List.of(new Position(2, 3), new Position(2, 4), new Position(2, 5))
    );

    private static final List<List<List<Integer>>> length1IndexPairs = List.of(
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
    public static final String INVALID_GUNGSUNG_PATH = "궁성 대각선 경로가 아닙니다.";

    @Override
    public Path calculatePath(Position source, Position destination) {
        if (!isPathPossible(source, destination)) {
            throw new JanggiException(INVALID_GUNGSUNG_PATH);
        }
        Direction direction = determineDirection(source, destination);
        if (!direction.isDiagonal()) {
            throw new JanggiException(INVALID_GUNGSUNG_PATH);
        }
        return buildPath(source, destination, direction);
    }

    @Override
    public boolean isPathPossible(Position source, Position destination) {
        for (List<List<Integer>> length1IndexPair : length1IndexPairs) {
            List<Integer> targetSourceIndex = length1IndexPair.getFirst();
            List<Integer> targetDestinationIndex = length1IndexPair.get(1);

            Position targetSource = choGungsungs.get(targetSourceIndex.getFirst())
                    .get(targetSourceIndex.get(1));
            Position targetDestination = choGungsungs.get(targetDestinationIndex.getFirst())
                    .get(targetDestinationIndex.get(1));

            if (source.equals(targetSource) && destination.equals(targetDestination)) {
                return true;
            }
        }

        for (List<List<Integer>> length1IndexPair : length1IndexPairs) {
            List<Integer> targetSourceIndex = length1IndexPair.getFirst();
            List<Integer> targetDestinationIndex = length1IndexPair.get(1);

            Position targetSource = hanGungsungs.get(targetSourceIndex.getFirst())
                    .get(targetSourceIndex.get(1));
            Position targetDestination = hanGungsungs.get(targetDestinationIndex.getFirst())
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
