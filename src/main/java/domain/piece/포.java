package domain.piece;

import domain.route.linear_route.포Route;

public class 포 extends Piece {
    public 포(JanggiSide side) {
        super(7, side, new 포Route());
    }
}
