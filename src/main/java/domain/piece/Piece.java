package domain.piece;

import domain.BoardLocation;

public class Piece {

    private final PieceType pieceType;

    public Piece(PieceType pieceType) {
        this.pieceType = pieceType;
    }

    public boolean isMovable(BoardLocation current, BoardLocation target) {
        return pieceType.isMovable(current, target);
    }

    @Override
    public boolean equals(Object obj) {
        return obj != null && this.getClass() == obj.getClass();
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
