package domain.piece;

import domain.pattern.상Path;
import domain.piece.state.Moved상;

public class 상 extends Piece {
    public 상(Side side) {
        super(3, side, new 상Path(), new Moved상());
    }
}
