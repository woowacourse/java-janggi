package janggi.point.crash;

import janggi.game.Team;
import janggi.point.Hurdles;
import janggi.point.Point;
import java.util.List;

public abstract class Crashes {
    protected final List<Point> crashes;

    public Crashes(List<Point> crashes) {
        this.crashes = crashes;
    }

    public abstract boolean hasNoCrashes(Team movingTeam, Point targetPoint, Hurdles hurdles);
}
