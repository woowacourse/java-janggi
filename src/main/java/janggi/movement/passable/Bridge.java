package janggi.movement.passable;

import janggi.movement.route.Hurdles;
import janggi.movement.route.Route;
import janggi.piece.Movable;

public class Bridge {
    private final Movable bridge;
    private final Movable mover;

    public Bridge(Movable bridge, Movable mover) {
        this.bridge = bridge;
        this.mover = mover;
    }

    public static Bridge from(Route route, Hurdles hurdles, Movable movingPiece) {
        Movable bridge = null;
        if (route.hasCrash(hurdles)) {
            bridge = route.findFirstCrash(hurdles);
        }
        return new Bridge(bridge, movingPiece);
    }
    
    public boolean cannotPass() {
        return bridge.isPo();
    }
}
