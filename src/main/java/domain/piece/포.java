package domain.piece;

import domain.route.linear_route.포Route;

public final class 포 extends JanggiPiece {

    public 포(final JanggiSide side) {
        super(7, side, new 포Route());
    }

    @Override
    public boolean is포() {
        return true;
    }
}
