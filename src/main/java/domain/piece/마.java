package domain.piece;

import domain.route.limited_route.마Route;

public class 마 extends JanggiPiece {

    public 마(JanggiSide side) {
        super(5, side, new 마Route());
    }
}
