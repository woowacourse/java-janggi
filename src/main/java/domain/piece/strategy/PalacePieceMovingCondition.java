package domain.piece.strategy;

import domain.board.BoardState;
import domain.direction.Direction;
import domain.direction.Directions;
import domain.position.Palace;
import domain.position.Position;

public class PalacePieceMovingCondition implements MovingCondition {

    private static final int MAX_DIRECTION = 1;

    @Override
    public boolean canMove(BoardState boardState, Position startPosition, Position endPosition) {
        Directions directions = Directions.of(startPosition, endPosition);
        if (directions.size() != MAX_DIRECTION) {
            return false;
        }

        Direction direction = directions.next();
        if (!Palace.isPalace(endPosition)) {
            return false;
        }
        return direction.isStraight() || Palace.canStepDiagonal(startPosition, direction);
    }
}
