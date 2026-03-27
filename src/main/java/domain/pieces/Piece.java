package domain.pieces;

import domain.Camp;

public class Piece {

    private final Camp camp;

    public Piece(Camp camp) {
        this.camp = camp;
    }

    public boolean isSameCamp(Piece comparedPiece) {
        return this.camp == comparedPiece.camp;
    }
}
