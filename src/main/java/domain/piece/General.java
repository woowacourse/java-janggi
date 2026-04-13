package domain.piece;

import domain.board.Country;

public class General extends SingleMovingPiece {
    public General(Country country) {
        super(new PieceInfo(PieceType.GENERAL, country));
    }
}
