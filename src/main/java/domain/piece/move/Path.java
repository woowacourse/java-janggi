package domain.piece.move;

import domain.position.Direction;
import domain.position.Position;
import java.util.ArrayList;
import java.util.List;

public class Path {

    private final List<Direction> movement;

    public Path(List<Direction> movement) {
        this.movement = movement;
    }

    public boolean canReachFromTo(Position startPosition, Position endPosition) {
        Position curPosition = startPosition;
        for (Direction direction : movement) {
            if (isInvalidMove(curPosition, direction)) {
                return false;
            }
            curPosition = curPosition.moveDirection(direction);
        }
        return curPosition.equals(endPosition);
    }

    public List<Position> findPathPositionsFrom(Position startPosition) {
        List<Position> pathPositions = new ArrayList<>();
        Position movePosition = startPosition;
        for (Direction direction : movement) {
            movePosition = movePosition.moveDirection(direction);
            pathPositions.add(movePosition);
        }

        pathPositions.removeLast();
        return pathPositions;
    }

    private boolean isInvalidMove(Position curPosition, Direction direction) {
        return !curPosition.canMovePosition(direction.getDeltaRow(), direction.getDeltaColumn());
    }
}
