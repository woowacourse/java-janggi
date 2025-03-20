package domain.piece;

import domain.route.limited_route.궁Route;

public final class 궁 extends JanggiPiece {

    public 궁(final JanggiSide side) {
        super(0, side, new 궁Route());
    }
}
