package domain.piece.strategy;

import domain.board.Direction;
import domain.piece.Piece;
import domain.position.Position;

import java.util.Map;

public class HorseMovingCondition implements MovingCondition {

    private final static int MAX_DIRECTION = 2;

    @Override
    public boolean canMove(Map<Position, Piece> state, Position startPosition, Position endPosition) {
        Directions directions = Directions.between(startPosition, endPosition);

        if (directions.size() != MAX_DIRECTION) {
            return false;
        }
        return hasValidHorsePath(state, startPosition, directions);
    }

    private boolean hasValidHorsePath(Map<Position, Piece> state, Position startPosition, Directions directions) {
        Direction firstDirection = directions.next();
        Direction secondDirection = directions.next();

        return firstDirection.isStraight()
                && isNotBlocked(state, startPosition, firstDirection)
                && secondDirection.isSameAtLeastOne(firstDirection);
    }

    private boolean isNotBlocked(Map<Position, Piece> state, Position position, Direction direction) {
        return !state.containsKey(position.append(direction));
    }
}
