package janggi.piece;

import janggi.direction.PieceMoveRule;
import janggi.direction.PieceType;
import janggi.piece.board.Board;
import janggi.position.Position;

public class Piece {

    private final PieceMoveRule pieceMoveRule;

    public Piece(final PieceMoveRule pieceMoveRule) {
        this.pieceMoveRule = pieceMoveRule;
    }

    public void validateMovement(final Position currentPosition, final Position arrivalPosition,
                                 final Board board) {
        pieceMoveRule.validatePath(currentPosition, arrivalPosition, board);
    }

    public boolean isObstacleJumping() {
        return pieceMoveRule.isObstacleJumping();
    }

    public boolean matchPieceMovement(final PieceType givenPieceType) {
        return getPieceType() == givenPieceType;
    }

    public PieceType getPieceType() {
        return pieceMoveRule.getPieceType();
    }
}
