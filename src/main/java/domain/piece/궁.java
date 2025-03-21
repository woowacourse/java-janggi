package domain.piece;

import domain.pattern.궁Path;
import domain.piece.state.Moved궁;

public class 궁 extends Piece {

    public 궁(Side side) {
        super(0, side, new 궁Path(), new Moved궁());
    }
}
