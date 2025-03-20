package domain.piece;

import domain.pattern.차Path;

public class 차 extends Piece {
    public 차(Side side) {
        super(13, side, new 차Path());
    }
}
