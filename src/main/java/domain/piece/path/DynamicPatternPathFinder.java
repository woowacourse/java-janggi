package domain.piece.path;

import domain.position.Direction;
import domain.position.Position;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class DynamicPatternPathFinder implements PathFinder {
    private final List<Direction> directions;
    private final Map<Position, List<Direction>> palace_directions;

    public DynamicPatternPathFinder(List<Direction> directions, Map<Position, List<Direction>> palaceDirections) {
        this.directions = directions;
        palace_directions = palaceDirections;
    }

    @Override
    public List<Position> findIntermediatePositions(Position from, Position to) {
        Direction directionToReachAt = findDirectionToReachAt(from, to);
        return findIntermediatePositions(directionToReachAt, from, to);
    }

    private Direction findDirectionToReachAt(Position from, Position to) {
        Optional<Direction> extractDirection = extractDirection(from, to, directions);
        if (extractDirection.isEmpty() && palace_directions.containsKey(from) && to.isInPalace()) {
            extractDirection = extractDirection(from, to, palace_directions.get(from));
        }
        return extractDirection.orElseThrow(() -> new IllegalArgumentException("해당 좌표로 이동시킬 수 없습니다."));
    }

    private Optional<Direction> extractDirection(Position from, Position to, List<Direction> directions) {
        for (Direction nextDirection : directions) {
            Direction findDirection = findDirection(from, to, nextDirection);
            if (findDirection != null) {
                return Optional.of(findDirection);
            }
        }
        return Optional.empty();
    }

    private Direction findDirection(Position from, Position to, Direction nextDirection) {
        Position current = from;
        while (current.canMovePosition(nextDirection.getDeltaRow(), nextDirection.getDeltaColumn())) {
            current = current.movePosition(nextDirection.getDeltaRow(), nextDirection.getDeltaColumn());
            if (current.equals(to)) {
                return nextDirection;
            }
        }
        return null;
    }

    private List<Position> findIntermediatePositions(Direction direction, Position from, Position to) {
        Position cur = from;
        List<Position> positions = new ArrayList<>();
        while (!cur.equals(to)) {
            cur = cur.movePosition(direction.getDeltaRow(), direction.getDeltaColumn());
            positions.add(cur);
        }
        positions.removeLast();
        return positions;
    }
}
