package model.janggiboard;

import model.piece.Piece;

public class Dot {

    private final Piece piece;

    public Dot() {
        piece = null;
    }

    public Dot(Piece piece) {
        this.piece = piece;
    }

    public boolean isPlaced() {
        return piece != null;
    }

    public Piece getPiece() {
        if (!isPlaced()) {
            throw new IllegalArgumentException("해당 점에는 장기말이 없습니다.");
        }
        return piece;
    }
}
