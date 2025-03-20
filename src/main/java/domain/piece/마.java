package domain.piece;

import domain.route.마Route;

public class 마 extends Piece {

    public 마(Side side) {
        super(5, side, new 마Route());
    }

}
