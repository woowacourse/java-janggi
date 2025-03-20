package domain.piece;

import domain.route.병Route;
import domain.route.졸Route;

public class 졸병 extends Piece {

    public 졸병(Side side) {
        super(2, side, new 졸Route());
        if (side == Side.HAN) {
            route = new 병Route();
        }
    }
}
