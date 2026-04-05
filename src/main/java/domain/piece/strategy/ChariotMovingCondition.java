package domain.piece.strategy;

import domain.board.BoardState;
import domain.direction.Directions;
import domain.position.Palace;
import domain.position.Position;

public class ChariotMovingCondition implements MovingCondition {

    @Override
    public boolean canMove(BoardState boardState, Position startPosition, Position endPosition) {
        Directions directions = Directions.of(startPosition, endPosition);

        if (Palace.canStepDiagonal(startPosition, directions.findFirst()) && !Palace.isPalace(endPosition)) {
            return false;
        }
        if (!directions.checkAllDirectionIsStraight() && !Palace.isPalace(endPosition)) {
            return false;
        }
        return hasValidChariotPath(boardState, endPosition, new LinePath(startPosition, directions));
    }

    private boolean hasValidChariotPath(BoardState boardState, Position endPosition, LinePath path) {
        while (path.hasNext()) {
            if (!path.moveForward()) {
                return false;
            }
            if (path.isAt(endPosition)) {
                return true;
            }
            if (path.isBlockedBy(boardState)) {
                return false;
            }
        }
        return true;
    }
}
