package domain.piece.strategy;

import domain.board.Direction;
import domain.piece.Piece;
import domain.position.Position;

import java.util.Map;
import java.util.Queue;

public class ElephantMovingCondition implements MovingCondition {
    private static final int MAX_DIRECTION = 3;

    @Override
    public boolean canMove(Map<Position, Piece> state, Position startPosition, Position endPosition) {
        Queue<Direction> directions = Direction.of(startPosition, endPosition);

        if (directions.size() != MAX_DIRECTION) {
            return false;
        }
        return hasValidDirectionAndPath(state, startPosition, directions);
    }

    private boolean hasValidDirectionAndPath(
            Map<Position, Piece> state,
            Position startPosition,
            Queue<Direction> directions
    ) {
        Direction firstDirection = directions.remove();
        Position nextPosition = startPosition.append(firstDirection);

        if (!firstDirection.isStraight() || isBlocked(state, nextPosition)) {
            return false;
        }

        Direction secondDirection = directions.remove();
        nextPosition = nextPosition.append(secondDirection);
        if (secondDirection.isNotSameAtLeastOne(firstDirection) || isBlocked(state, nextPosition)) {
            return false;
        }

        return directions.remove() == secondDirection;
    }

    private boolean isBlocked(Map<Position, Piece> state, Position position) {
        return state.containsKey(position);
    }
}
