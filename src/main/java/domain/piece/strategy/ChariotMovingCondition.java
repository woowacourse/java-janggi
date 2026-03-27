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

        Direction firstDirection = directions.poll();
        if (!firstDirection.isStraight()) {
            return false;
        }
        Position firstPosition = startPosition.append(firstDirection);
        if (state.containsKey(firstPosition)) {
            return false;
        }

        Queue<Position> currentPositions = new ArrayDeque<>();
        currentPositions.offer(firstPosition);

        while (!directions.isEmpty()) {
            Direction currentDirection = directions.poll();
            if (currentDirection != firstDirection) {
                return false;
            }
            Position nextPosition = currentPositions.poll().append(currentDirection);
            if (nextPosition.equals(endPosition)) {
                return true;
            }
            if (state.containsKey(nextPosition)) {
                return false;
            }
            currentPositions.offer(nextPosition);
        }
        return true;
    }
}
