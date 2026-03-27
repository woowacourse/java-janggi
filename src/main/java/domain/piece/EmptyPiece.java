package domain.piece;

import domain.Position;

public class EmptyPiece implements Piece {

    @Override
    public boolean canMove(Position source, Position target) {
        return false;
    }

    @Override
    public boolean isNotEmpty() {
        return false;
    }

    @Override
    public String toString() {
        return PieceType.EMPTY.getDisplayName();
    }
}
