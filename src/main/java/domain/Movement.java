package domain;

import java.util.ArrayList;
import java.util.List;

public class Movement {
    private final List<Direction> movement;

    public Movement(List<Direction> movement) {
        this.movement = movement;
    }

    public List<Position> findIntermediatePositions(Position startPosition, Position endPosition) {
        if (isValidMove(startPosition, endPosition)) {
            return getPathPositionsFrom(startPosition);
        }
        throw new IllegalArgumentException("지정한 포지션으로 이동할 수 없습니다.");
    }

    public boolean isValidMove(Position startPosition, Position endPosition) {
        Position curPosition = startPosition;
        for (Direction direction : movement) {
            if (isInvalidMove(curPosition, direction)) {
                return false;
            }
            curPosition = curPosition.movePosition(direction.getDeltaRow(), direction.getDeltaColumn());
        }
        return curPosition.equals(endPosition);
    }

    private List<Position> getPathPositionsFrom(Position startPosition) {
        List<Position> pathPositions = new ArrayList<>();

        for (Direction direction : movement) {
            startPosition = startPosition.movePosition(direction.getDeltaRow(), direction.getDeltaColumn());
            pathPositions.add(startPosition);
        }

        pathPositions.removeLast();
        return pathPositions;
    }

    private boolean isInvalidMove(Position curPosition, Direction direction) {
        return !curPosition.canMovePosition(direction.getDeltaRow(), direction.getDeltaColumn());
    }
}
