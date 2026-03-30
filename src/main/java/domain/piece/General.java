package domain.piece;

import domain.Country;

public class General extends MoveOneStepPiece {
    public General(Country country) {
        super(new PieceInfo(PieceType.GENERAL, country));
    }
}
