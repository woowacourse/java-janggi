package domain.piece.strategy;

import domain.board.Direction;
import domain.piece.Piece;
import domain.position.Position;

import java.util.Map;

public class ElephantMovingCondition implements MovingCondition {
    private static final int MAX_DIRECTION = 3;

    @Override
    public boolean canMove(Map<Position, Piece> state, Position startPosition, Position endPosition) {
        Directions directions = Directions.between(startPosition, endPosition);

        if (directions.size() != MAX_DIRECTION) {
            return false;
        }
        return hasValidElephantPath(state, startPosition, directions);
    }

    private boolean hasValidElephantPath(Map<Position, Piece> state, Position startPosition, Directions directions) {
        Direction firstDirection = directions.next();
        Direction secondDirection = directions.next();
        Direction thirdDirection = directions.next();

        return firstDirection.isStraight()
                && isNotBlocked(state, startPosition, firstDirection)
                && secondDirection.isSameAtLeastOne(firstDirection)
                && isNotBlocked(state, startPosition.append(firstDirection), secondDirection)
                && secondDirection.isSameDirection(thirdDirection);
    }

    private boolean isNotBlocked(Map<Position, Piece> state, Position position, Direction direction) {
        return !state.containsKey(position.append(direction));
    }
}
