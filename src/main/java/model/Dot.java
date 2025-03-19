package model;

import java.util.Optional;

public class Dot {

    private Optional<Piece> piece;

    public Dot() {
        piece = Optional.empty();
    }

    public void place(Piece piece) {
        this.piece = Optional.ofNullable(piece);
    }

    public boolean isPlaced() {
        return piece.isPresent();
    }
}
