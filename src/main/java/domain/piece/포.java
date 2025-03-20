package domain.piece;

import domain.route.포Route;

public class 포 extends Piece {
    public 포(Side side) {
        super(7, side, new 포Route());
    }
}
