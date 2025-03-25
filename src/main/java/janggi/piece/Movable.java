package janggi.piece;

import janggi.game.Team;
import janggi.movement.route.Hurdles;
import janggi.point.Point;

public abstract class Movable {

    protected static final int MAX_COLUMN_LOCATION = 9;

    protected final Team team;
    protected final Point point;

    public Movable(Team team, Point point) {
        this.team = team;
        this.point = point;
    }

    public abstract boolean canMove(Point targetPoint, Hurdles hurdles);

    public abstract Movable updatePoint(Point afterPoint);

    public abstract String getName();

    public boolean isPo() {
        return false;
    }

    public Point getPoint() {
        return point;
    }

    public Team getTeam() {
        return team;
    }
}
