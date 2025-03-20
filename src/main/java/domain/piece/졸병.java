package domain.piece;

import domain.pattern.병Path;
import domain.pattern.졸Path;

public class 졸병 extends Piece {

    public 졸병(Side side) {
        super(2, side, new 졸Path());
        if (side == Side.HAN) {
            path = new 병Path();
        }
    }
}
