package janggi.piece;

import janggi.direction.PieceMoveRule;
import janggi.direction.PieceType;
import janggi.piece.board.Board;
import janggi.position.Position;

public class Piece {

    private final PieceMoveRule pieceMoveRule;
    private Position position;

    public Piece(final PieceMoveRule pieceMoveRule, final Position position) {
        this.pieceMoveRule = pieceMoveRule;
        this.position = position;
    }

    public boolean isSamePosition(final Position givenPosition) {
        return position.equals(givenPosition);
    }

    public void updatePosition(final Position arrivalPosition) {
        position = arrivalPosition;
    }

    public void validateMovement(final Position currentPosition, final Position arrivalPosition,
                                 final Board board) {
        pieceMoveRule.validatePath(currentPosition, arrivalPosition, board);
    }

    public boolean isObstacleJumping() {
        return getPieceType() == PieceType.CANNON;
    }

    public boolean matchPieceMovement(final PieceType givenPieceType) {
        return getPieceType() == givenPieceType;
    }


    public Position getPosition() {
        return position;
    }

    public PieceType getPieceType() {
        return pieceMoveRule.getPieceType();
    }
}
