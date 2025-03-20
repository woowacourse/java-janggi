package domain.piece;

import domain.route.limited_route.병Route;
import domain.route.limited_route.졸Route;

public final class 졸병 extends JanggiPiece {

    public 졸병(final JanggiSide side) {
        super(2, side, new 졸Route());
        if (side == JanggiSide.HAN) {
            route = new 병Route();
        }
    }
}
