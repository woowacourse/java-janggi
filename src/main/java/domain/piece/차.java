package domain.piece;

import domain.route.차Route;

public class 차 extends Piece {
    public 차(Side side) {
        super(13, side, new 차Route());
    }
}
