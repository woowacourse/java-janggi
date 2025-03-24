package janggi.movement.crash;

import janggi.game.Team;
import janggi.movement.route.Hurdles;
import janggi.piece.Movable;
import janggi.point.Point;
import java.util.List;

public abstract class Crashes {
    protected final List<Point> crashes;

    public Crashes(List<Point> crashes) {
        this.crashes = crashes;
    }

    public static Crashes fromPieceType(Movable movable, List<Point> crashPoints) {
        if (movable.isPo()) {
            return new CrashOrBridgeAndPrey(crashPoints);
        }
        return new CrashOrPrey(crashPoints);
    }

    public abstract boolean hasNoCrashes(Team movingTeam, Point targetPoint, Hurdles hurdles);
}
