package domain.piece;

import domain.route.limited_route.상Route;

public class 상 extends JanggiPiece {
    public 상(JanggiSide side) {
        super(3, side, new 상Route());
    }
}
