package domain.piece.path;

import domain.position.Direction;
import domain.position.Position;
import java.util.ArrayList;
import java.util.List;

public class DynamicPatternPathFinder implements PathFinder {
    private final List<Direction> directions;

    public DynamicPatternPathFinder(List<Direction> directions) {
        this.directions = directions;
    }

    @Override
    public List<Position> findIntermediatePositions(Position from, Position to) {
        Direction directionToReachAt = findDirectionToReachAt(from, to);
        return findIntermediatePositions(directionToReachAt, from, to);
    }

    private Direction findDirectionToReachAt(Position from, Position to) {
        for (Direction nextDirection : directions) {
            Direction findDirection = findDirection(from, to, nextDirection);
            if (findDirection != null) {
                return findDirection;
            }
        }
        throw new IllegalArgumentException("해당 좌표로 이동시킬 수 없습니다.");
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
