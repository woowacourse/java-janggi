package domain.piece;

import domain.route.limited_route.궁Route;

public class 궁 extends Piece {

    public 궁(Side side) {
        super(0, side, new 궁Route());
    }
}
