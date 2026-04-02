package domain.piece.strategy;

import domain.board.BoardState;
import domain.board.Direction;
import domain.position.Position;
import domain.position.PositionCalculator;

public class ElephantMovingCondition implements MovingCondition {
    private static final int MAX_DIRECTION = 3;

    @Override
    public boolean canMove(BoardState boardState, Position startPosition, Position endPosition) {
        Directions directions = Directions.between(startPosition, endPosition);

        if (directions.size() != MAX_DIRECTION) {
            return false;
        }
        return hasValidElephantPath(boardState, startPosition, directions);
    }

    private boolean hasValidElephantPath(BoardState boardState, Position startPosition, Directions directions) {
        Direction firstDirection = directions.next();
        Direction secondDirection = directions.next();
        Direction thirdDirection = directions.next();

        return firstDirection.isStraight()
                && isNotBlocked(boardState, startPosition, firstDirection)
                && secondDirection.isSameAtLeastOne(firstDirection)
                && isNotBlocked(boardState, PositionCalculator.add(startPosition, firstDirection), secondDirection)
                && secondDirection.isSameDirection(thirdDirection);
    }

    private boolean isNotBlocked(BoardState boardState, Position position, Direction direction) {
        return !boardState.isBlocked(PositionCalculator.add(position, direction));
    }
}
