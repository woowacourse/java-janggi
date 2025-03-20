package domain.piece;

import domain.route.limited_route.사Route;

public final class 사 extends JanggiPiece {

    public 사(final JanggiSide side) {
        super(3, side, new 사Route());
    }
}
