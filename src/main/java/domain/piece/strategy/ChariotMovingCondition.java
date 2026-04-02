package domain.piece.strategy;

import domain.board.BoardState;
import domain.position.Position;

public class ChariotMovingCondition implements MovingCondition {

    @Override
    public boolean canMove(BoardState boardState, Position startPosition, Position endPosition) {
        Directions directions = Directions.between(startPosition, endPosition);
        if (!directions.checkAllDirectionIsStraight()) {
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
