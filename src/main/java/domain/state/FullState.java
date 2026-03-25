package domain.state;

import domain.piece.Piece;

public class FullState implements State {
    private final Piece piece;

    public FullState(Piece piece) {
        this.piece = piece;
    }

    public Piece getPiece() {
        return piece;
    }
}
