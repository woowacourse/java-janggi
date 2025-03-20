package domain.piece;

import domain.route.limited_route.마Route;

public final class 마 extends JanggiPiece {

    public 마(final JanggiSide side) {
        super(5, side, new 마Route());
    }
}
