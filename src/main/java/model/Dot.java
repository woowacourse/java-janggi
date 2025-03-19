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

    public Piece getPiece() {
        if (!isPlaced()) {
            throw new IllegalArgumentException("해당 점에는 장기말이 없습니다.");
        }
        return piece.get();
    }
}
