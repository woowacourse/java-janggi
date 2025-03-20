package domain.piece;

import domain.route.limited_route.상Route;

public class 상 extends Piece {
    public 상(Side side) {
        super(3, side, new 상Route());
    }
}
