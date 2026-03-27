package domain.piece.strategy;

import domain.board.Direction;
import domain.piece.Piece;
import domain.position.Position;

import java.util.ArrayDeque;
import java.util.Map;
import java.util.Queue;

public class HorseMovingCondition implements MovingCondition {

    private final static int MAX_DIRECTION = 2;

    @Override
    public boolean canMove(Map<Position, Piece> state, Position startPosition, Position endPosition) {
        Queue<Direction> directions = Direction.of(startPosition, endPosition);

        if (directions.size() != MAX_DIRECTION) {
            return false;
        }

        Direction firstDirection = directions.poll();
        if (!firstDirection.isStraight()) {
            return false;
        }

        Position nextPosition = startPosition.append(firstDirection);
        if (state.containsKey(nextPosition)) {
            return false;
        }

        Direction secondDirection = directions.poll();
        if (!secondDirection.isSameAtLeastOne(firstDirection) || secondDirection.isStraight()) {
            return false;
        }

        return true;
    }
}
