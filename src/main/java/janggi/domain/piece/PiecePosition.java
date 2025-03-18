package janggi.domain.piece;

import janggi.domain.Position;

public class PiecePosition {

    private final Position position;
    private final Piece piece;

    public PiecePosition(Position position, Piece piece) {
        this.position = position;
        this.piece = piece;
    }

    public String toName() {
        return piece.toName();
    }
}
