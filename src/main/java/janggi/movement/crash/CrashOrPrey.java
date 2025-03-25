package janggi.movement.crash;

import janggi.game.Team;
import janggi.movement.route.Hurdles;
import janggi.piece.Movable;
import janggi.point.Point;
import java.util.List;

public final class CrashOrPrey extends Crashes {
    private static final int ONLY_PREY_COUNT = 1;

    public CrashOrPrey(List<Point> crashes) {
        super(crashes);
    }

    @Override
    public boolean hasOnlyPassables(Team movingTeam, Point targetPoint, Hurdles hurdles) {
        return isPreyOnly(movingTeam, targetPoint, hurdles);
    }

    private boolean isPreyOnly(Team movingTeam, Point targetPoint, Hurdles hurdles) {
        if (crashes.size() == ONLY_PREY_COUNT) {
            Point preyPoint = crashes.getFirst();
            Movable prey = hurdles.findByPoint(preyPoint);
            return isPreyInTargetPoint(targetPoint, preyPoint)
                    && isPreyOtherTeam(movingTeam, prey);
        }
        return false;
    }
}
