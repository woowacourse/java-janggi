package domain.piece;

import domain.pattern.병Path;
import domain.pattern.졸Path;
import domain.piece.state.Moved병;
import domain.piece.state.Moved졸;

public class 졸병 extends Piece {

    public 졸병(Side side) {
        super(2, side, new 졸Path(), new Moved졸());
        if (side == Side.HAN) {
            path = new 병Path();
            state = new Moved병();
        }
    }
}
