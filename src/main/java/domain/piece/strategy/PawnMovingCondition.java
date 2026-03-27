package domain.piece.strategy;

import domain.board.Direction;
import domain.piece.Piece;
import domain.piece.Side;
import domain.position.Position;

import java.util.Map;
import java.util.Queue;

public class PawnMovingCondition implements MovingCondition {

    private static final int MAX_DIRECTION = 1;

    @Override
    public boolean canMove(Map<Position, Piece> state, Position startPosition, Position endPosition) {
        Queue<Direction> directions = Direction.of(startPosition, endPosition);
        if (directions.size() != MAX_DIRECTION) {
            return false;
        }

        Direction direction = directions.poll();
        if (!(direction == Direction.UP || direction == Direction.LEFT || direction == Direction.RIGHT)) {
            return false;
        }
        return true;
    }
}
