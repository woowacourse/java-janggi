package domain.piece.strategy;

import domain.board.BoardState;
import domain.direction.Directions;
import domain.piece.Piece;
import domain.piece.PieceType;
import domain.position.Palace;
import domain.position.Position;

public class CanonMovingCondition implements MovingCondition {
    private static final int MAX_PIECE_COUNT_ON_THE_PATH = 1;

    @Override
    public boolean canMove(BoardState boardState, Position startPosition, Position endPosition) {
        Directions directions = Directions.of(startPosition, endPosition);

        if (Palace.canStepDiagonal(startPosition, directions.findFirst()) && !Palace.isPalace(endPosition)) {
            return false;
        }
        if (!directions.checkAllDirectionIsStraight() && !Palace.isPalace(endPosition)) {
            return false;
        }
        return hasValidCanonPath(boardState, endPosition, new LinePath(startPosition, directions));
    }

    private boolean hasValidCanonPath(BoardState boardState, Position endPosition, LinePath path) {
        int pieceCount = 0;

        while (path.hasNext()) {
            if (!isValidPath(boardState, path)) {
                return false;
            }
            if (isPieceInPath(boardState, endPosition, path)) {
                pieceCount++;
            }
            if (pieceCount > MAX_PIECE_COUNT_ON_THE_PATH) {
                return false;
            }
        }
        return pieceCount == MAX_PIECE_COUNT_ON_THE_PATH;
    }

    private boolean isValidPath(BoardState boardState, LinePath path) {
        if (!path.moveForward()) {
            return false;
        }
        Piece piece = path.findCurrentPiece(boardState);
        return piece == null || !piece.isSamePieceType(PieceType.CANON);
    }

    private boolean isPieceInPath(BoardState boardState, Position endPosition, LinePath path) {
        return path.isBlockedBy(boardState) && !path.isAt(endPosition);
    }
}
