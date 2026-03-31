package domain.piece;

import domain.Country;

public class Guard extends MoveOneSpacePiece {
    public Guard(Country country) {
        super(new PieceInfo(PieceType.GUARD, country));
    }
}
