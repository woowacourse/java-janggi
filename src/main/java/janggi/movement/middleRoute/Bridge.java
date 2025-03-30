package janggi.movement.middleRoute;

import janggi.piece.Piece;

public class Bridge {
    private final Piece bridge;
    private final Piece mover;

    public Bridge(Piece bridge, Piece mover) {
        this.bridge = bridge;
        this.mover = mover;
    }

    public static Bridge from(Route route, Hurdles hurdles, Piece movingPiece) {
        Piece bridge = null;
        if (route.hasCrash(hurdles)) {
            bridge = route.findFirstCrash(hurdles);
        }
        return new Bridge(bridge, movingPiece);
    }

    public boolean cannotPass() {
        if (bridge == null) {
            return true;
        }
        return mover.isPo() & bridge.isPo();
    }
}
