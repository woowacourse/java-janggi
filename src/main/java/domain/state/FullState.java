package domain.state;

import domain.piece.Piece;

public record FullState(Piece piece) implements State {
    public Piece getPiece() {
        return piece;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }
}
