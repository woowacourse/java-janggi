package janggi.piece;

import janggi.point.Direction;
import janggi.point.Hurdles;
import janggi.point.InitialPoint;
import janggi.point.Point;
import janggi.game.Team;
import janggi.point.PointDistance;
import janggi.point.Route;
import java.util.ArrayList;
import java.util.List;

public class Gung implements Movable {

    private static final String NAME = "궁";
    private static final List<Gung> gungs;

    private final Team team;
    private final Point point;

    public Gung(Team team, Point point) {
        this.team = team;
        this.point = point;
    }

    static {
        List<Gung> createdPieces = new ArrayList<>();
        for (Point point : InitialPoint.GUNG.getRedPoints()) {
            createdPieces.add(new Gung(Team.HAN, point));
        }
        for (Point point : InitialPoint.GUNG.getBluePoints()) {
            createdPieces.add(new Gung(Team.CHO, point));
        }
        gungs = createdPieces;
    }

    public static List<Gung> values() {
        return new ArrayList<>(gungs);
    }

    @Override
    public boolean canMove(Point targetPoint, Hurdles hurdles) {
        if (isDistanceOverFlow(targetPoint)) {
            return false;
        }
        Direction direction = Direction.cardinalOrDiagonalFrom(this.point, targetPoint);
        return isRouteHaveNoHurdle(targetPoint, hurdles, direction);
    }

    private boolean isDistanceOverFlow(Point targetPoint) {
        PointDistance distance = PointDistance.calculate(point, targetPoint);
        return !distance.isSameWith(1) && !distance.isSameWith(Math.sqrt(2));
    }

    private boolean isRouteHaveNoHurdle(Point targetPoint, Hurdles hurdles, Direction direction) {
        Route route = Route.repeat(direction, this.point, targetPoint);
        return route.hasNoHurdle(this, targetPoint, hurdles);
    }

    @Override
    public Movable updatePoint(Point afterPoint) {
        return new Gung(team, afterPoint);
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public Point getPoint() {
        return point;
    }

    @Override
    public Team getTeam() {
        return this.team;
    }
}
