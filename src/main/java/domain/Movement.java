package domain;

import java.util.ArrayList;
import java.util.List;

public class Movement {
    private final List<Direction> movement;

    public Movement(List<Direction> movement) {
        this.movement = movement;
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

    public List<Position> findIntermediatePositions(Position startPosition, Position endPosition) {
        if (isValidMove(startPosition, endPosition)) {
            List<Position> positions = new ArrayList<>();
            Position curPosition = startPosition;
            for (Direction direction : movement) {
                curPosition = curPosition.movePosition(direction.getDeltaRow(), direction.getDeltaColumn());
                positions.add(curPosition);
            }
            positions.removeLast();
            return positions;
        }
        throw new IllegalArgumentException("지정한 포지션으로 이동할 수 없습니다.");
    }

    private boolean isInvalidMove(Position curPosition, Direction direction) {
        return !curPosition.canMovePosition(direction.getDeltaRow(), direction.getDeltaColumn());
    }
}
