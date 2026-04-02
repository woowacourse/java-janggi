package domain.piece.strategy;

import domain.board.BoardState;
import domain.board.Direction;
import domain.position.Position;

public class PawnMovingCondition implements MovingCondition {

    private static final int MAX_DIRECTION = 1;

    @Override
    public boolean canMove(BoardState boardState, Position startPosition, Position endPosition) {
        Directions directions = Directions.between(startPosition, endPosition);
        if (directions.size() != MAX_DIRECTION) {
            return false;
        }

        Direction currentDirection = directions.next();
        return currentDirection != Direction.DOWN;
    }
}
