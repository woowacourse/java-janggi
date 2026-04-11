package domain.pieces;

import domain.pieces.exception.NoPieceException;
import domain.pieces.exception.PieceErrorMessage;
import domain.movepolicy.MoveContext;
import domain.position.Position;

public record EmptyPiece() implements Piece {

    @Override
    public boolean isEmpty() {
        return true;
    }

    @Override
    public boolean isSameSide(Piece other) {
        return false;
    }

    @Override
    public boolean isPo() {
        return false;
    }

    @Override
    public MoveContext askMoveContext(Position departure, Position destination) {
        throw new NoPieceException(PieceErrorMessage.NO_PIECE);
    }

    @Override
    public PieceType getType() {
        return PieceType.EMPTY;
    }

    @Override
    public Side getSide() {
        return null;
    }
}
