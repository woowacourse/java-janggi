package domain.piece;

import domain.route.limited_route.병Route;
import domain.route.limited_route.졸Route;

public class 졸병 extends Piece {

    public 졸병(JanggiSide side) {
        super(2, side, new 졸Route());
        if (side == JanggiSide.HAN) {
            route = new 병Route();
        }
    }
}
