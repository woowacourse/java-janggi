package domain.piece.strategy;

import domain.board.Direction;
import domain.piece.Piece;
import domain.position.Position;

import java.util.ArrayDeque;
import java.util.Map;
import java.util.Queue;

public class ChariotMovingCondition implements MovingCondition {

    @Override
    public boolean canMove(Map<Position, Piece> state, Position startPosition, Position endPosition) {
        Queue<Direction> directions = Direction.of(startPosition, endPosition);

        if (!isInPalaceEdgePathAtLeastOne(startPosition, endPosition) && !isStraightDirection(directions)) return false;
        return hasValidChariotPath(state, startPosition, endPosition, directions);
    }

    private boolean hasValidChariotPath(
            Map<Position, Piece> state,
            Position startPosition,
            Position endPosition,
            Queue<Direction> directions
    ) {
        Direction standardDirection = directions.peek();
        Position currentPosition = startPosition;

        while (!directions.isEmpty()) {
            Direction currentDirection = directions.poll();
            if (currentDirection != standardDirection) return false;

            currentPosition = currentPosition.append(currentDirection);

            if (currentPosition.equals(endPosition)) return true;
            if (state.containsKey(currentPosition)) return false;
        }
        return true;
    }

    private boolean isInPalaceEdgePathAtLeastOne(Position startPosition, Position endPosition) {
        return startPosition.isPalaceEdge() || endPosition.isPalaceEdge();
    }

    private boolean isStraightDirection(Queue<Direction> directions) {
        return !directions.isEmpty() && directions.peek().isStraight();
    }
}

