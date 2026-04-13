package domain.piece;

import domain.board.Country;

public class Chariot extends StraightMovingPiece {
    public Chariot(Country country) {
        super(new PieceInfo(PieceType.CHARIOT, country));
    }
}
