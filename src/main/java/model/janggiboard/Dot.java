package model.janggiboard;

import java.util.Optional;
import model.piece.Piece;

public class Dot {
    private Piece piece;

    public Dot() {
        piece = null;
    }

    public void place(Piece piece) {
        this.piece = piece;
    }

    public boolean isPlaced() {
        return piece != null;
    }

    public Optional<Piece> getPiece() {
        return Optional.ofNullable(piece);
    }

    public void clear() {
        this.piece = null;
    }
}
