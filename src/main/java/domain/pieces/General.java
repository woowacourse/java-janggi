package domain.pieces;

import domain.Camp;
import domain.PieceType;

public class General extends SingleStepPiece {

    public General(Camp camp) {
        super(camp, PieceType.GENERAL);
    }
}
