package domain.piece;

import domain.route.사Route;

public class 사 extends Piece {
    public 사(Side side) {
        super(3, side, new 사Route());
    }
}
