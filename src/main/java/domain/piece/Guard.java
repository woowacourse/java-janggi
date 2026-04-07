package domain.piece;

import domain.Camp;
import domain.PieceType;

public class Guard extends SingleStepPiece {

    public Guard(Camp camp) {
        super(camp, PieceType.GUARD);
    }
}
