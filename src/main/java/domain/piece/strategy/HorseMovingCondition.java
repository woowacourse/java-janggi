package domain.piece.strategy;

import domain.board.BoardState;
import domain.direction.Direction;
import domain.direction.Directions;
import domain.position.Position;

public class HorseMovingCondition implements MovingCondition {

    private final static int MAX_DIRECTION = 2;

    @Override
    public boolean canMove(BoardState boardState, Position startPosition, Position endPosition) {
        Directions directions = Directions.of(startPosition, endPosition);

        if (directions.size() != MAX_DIRECTION) {
            return false;
        }
        return hasValidHorsePath(boardState, startPosition, directions);
    }

    private boolean hasValidHorsePath(BoardState boardState, Position startPosition, Directions directions) {
        Direction firstDirection = directions.next();
        Direction secondDirection = directions.next();

        return firstDirection.isStraight()
                && isNotBlocked(boardState, startPosition, firstDirection)
                && secondDirection.isSameAtLeastOne(firstDirection);
    }

    private boolean isNotBlocked(BoardState boardState, Position position, Direction direction) {
        return !boardState.isBlocked(position.append(direction));
    }
}
