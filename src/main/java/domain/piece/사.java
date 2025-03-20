package domain.piece;

import domain.route.limited_route.사Route;

public class 사 extends JanggiPiece {

    public 사(JanggiSide side) {
        super(3, side, new 사Route());
    }
}
