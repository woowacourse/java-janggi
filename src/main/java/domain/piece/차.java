package domain.piece;

import domain.route.linear_route.차Route;

public final class 차 extends JanggiPiece {

    public 차(final JanggiSide side) {
        super(13, side, new 차Route());
    }
}
