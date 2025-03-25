package janggi.movement.crash;

import janggi.game.Team;
import janggi.movement.route.Hurdles;
import janggi.piece.Movable;
import janggi.point.Point;
import java.util.List;

public final class CrashOrBridgeAndPrey extends Crashes {
    private static final int ONLY_BRIDGE_COUNT = 1;
    private static final int ONLY_BRIDGE_AND_PREY_COUNT = 2;

    public CrashOrBridgeAndPrey(List<Point> crashes) {
        super(crashes);
    }

    @Override
    public boolean hasOnlyPassables(Team movingTeam, Point targetPoint, Hurdles hurdles) {
        return isBridgeOnly(targetPoint, hurdles)
                || isBridgeAndPreyOnly(movingTeam, targetPoint, hurdles);
    }

    private boolean isBridgeOnly(Point targetPoint, Hurdles hurdles) {
        if (crashes.size() == ONLY_BRIDGE_COUNT) {
            return isBridgeExists(targetPoint, hurdles);
        }
        return false;
    }

    private boolean isBridgeAndPreyOnly(Team movingTeam, Point targetPoint, Hurdles hurdles) {
        if (crashes.size() == ONLY_BRIDGE_AND_PREY_COUNT) {
            return (isBridgeExists(targetPoint, hurdles)
                    && isPreyExists(movingTeam, targetPoint, hurdles));
        }
        return false;
    }

    private boolean isBridgeExists(Point targetPoint, Hurdles hurdles) {
        Point bridgePoint = crashes.getFirst();
        if (hurdles.findByPoint(bridgePoint).isPo()
                || bridgePoint.equals(targetPoint)
        ) {
            return false;
        }
        return true;
    }

    private boolean isPreyExists(Team movingTeam, Point targetPoint, Hurdles hurdles) {
        Point preyPoint = crashes.getLast();
        Movable prey = hurdles.findByPoint(preyPoint);
        if (prey.isPo()) {
            return false;
        }
        return isPreyInTargetPoint(targetPoint, preyPoint)
                && isPreyOtherTeam(movingTeam, prey);
    }
}
