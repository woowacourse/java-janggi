package domain.piece.strategy;

import domain.piece.Piece;
import domain.position.Position;

import java.util.Map;

public class CanonMovingCondition implements MovingCondition {
    private static final int MAX_PIECE_COUNT_ON_THE_PATH = 1;

    @Override
    public boolean canMove(Map<Position, Piece> state, Position startPosition, Position endPosition) {
        Directions directions = Directions.between(startPosition, endPosition);

        if (!directions.isStraightDirection()) {
            return false;
        }
        return hasValidCanonPath(state, endPosition, new LinePath(startPosition, directions));
    }

    private boolean hasValidCanonPath(Map<Position, Piece> state, Position endPosition, LinePath path) {
        int pieceCount = 0;

        while (path.hasNext()) {
            if (!isValidPath(state, path)) {
                return false;
            }
            if (isPieceInPath(state, endPosition, path)) {
                pieceCount++;
            }
            if (pieceCount > MAX_PIECE_COUNT_ON_THE_PATH) {
                return false;
            }
        }
        return pieceCount == MAX_PIECE_COUNT_ON_THE_PATH;
    }

    private boolean isValidPath(Map<Position, Piece> state, LinePath path) {
        if (!path.moveForward()) {
            return false;
        }
        return !path.isBlockedByCanon(state);
    }

    private boolean isPieceInPath(Map<Position, Piece> state, Position endPosition, LinePath path) {
        return path.isBlockedBy(state) && !path.isAt(endPosition);
    }
}
