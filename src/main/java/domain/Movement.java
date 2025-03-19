package domain;

import java.util.List;

public class Movement {
    private final List<Direction> movement;

    public Movement(List<Direction> movement) {
        this.movement = movement;
    }

    public boolean isValidMove(Position startPosition, Position endPosition) {
        Position curPosition = startPosition;
        for (Direction direction : movement) {
            if(isInvalidMove(curPosition, direction)){
                return false;
            }
            curPosition = curPosition.movePosition(direction.getDeltaRow(), direction.getDeltaColumn());
        }
        return curPosition.equals(endPosition);
    }

    private static boolean isInvalidMove(Position curPosition, Direction direction) {
        return !curPosition.canMovePosition(direction.getDeltaRow(), direction.getDeltaColumn());
    }
}
