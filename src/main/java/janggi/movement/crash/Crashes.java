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

    public abstract boolean hasOnlyPassables(Team movingTeam, Point targetPoint, Hurdles hurdles);

    protected boolean isPreyOtherTeam(Team movingTeam, Movable preyPiece) {
        return movingTeam != preyPiece.getTeam();
    }

    protected boolean isPreyInTargetPoint(Point targetPoint, Point preyPoint) {
        return preyPoint.equals(targetPoint);
    }

    public int count() {
        return crashes.size();
    }
}
