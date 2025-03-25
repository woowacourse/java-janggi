package domain.piece;

import domain.pattern.GeneralPath;
import domain.piece.state.MovedGeneral;

public class General extends Piece {

    public General(Side side) {
        super(0, side, new GeneralPath(), new MovedGeneral(side));
    }
}
