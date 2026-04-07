package domain.piece.strategy;

import domain.board.Direction;
import domain.piece.Piece;
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

        Direction direction = directions.remove();
        if (direction.isDownForward()) {
            return false;
        }

        if (isInPalacePath(startPosition, endPosition) && isEndToEdgeCenterByDiagonal(endPosition, direction)) {
            return false;
        }

        return !isNotInPalacePathAndDiagonal(startPosition, endPosition, direction);
    }

    private boolean isNotInPalacePathAndDiagonal(Position startPosition, Position endPosition, Direction direction) {
        return !isInPalacePath(startPosition, endPosition) && direction.isDiagonal();
    }

    private boolean isInPalacePath(Position startPosition, Position endPosition) {
        return startPosition.isInPalace() && endPosition.isInPalace();
    }

    private boolean isEndToEdgeCenterByDiagonal(Position endPoint, Direction direction) {
        return endPoint.isPalaceEdgeCenter() && direction.isDiagonal();
    }
}
