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
        if (!canFirstStep(state, startPosition, firstDirection)) {
            return false;
        }

        Position firstPosition = startPosition.append(firstDirection);
        Direction secondDirection = directions.poll();
        if (!canSecondStep(state, firstPosition, firstDirection, secondDirection)) {
            return false;
        }
        return canLastStep(directions, secondDirection);
    }

    private boolean canFirstStep(Map<Position, Piece> state, Position startPosition, Direction nextDirection) {
        if (!nextDirection.isStraight()) {
            return false;
        }
        Position nextPosition = startPosition.append(nextDirection);
        return !isBlocked(state, nextPosition);
    }

    private boolean canSecondStep(Map<Position, Piece> state, Position firstPosition, Direction firstDirection, Direction secondDirection) {
        if (!firstDirection.isSameAtLeastOne(secondDirection)) {
            return false;
        }
        Position secondPosition = firstPosition.append(secondDirection);
        return !isBlocked(state, secondPosition);
    }

    private boolean canLastStep(Queue<Direction> directions, Direction secondDirection) {
        return directions.poll() == secondDirection;
    }

    private boolean isBlocked(Map<Position, Piece> state, Position position) {
        return state.containsKey(position);
    }
}
