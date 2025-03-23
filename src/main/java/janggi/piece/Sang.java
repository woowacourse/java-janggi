package janggi.piece;

import janggi.point.crash.Crashes;
import janggi.point.Direction;
import janggi.point.Hurdles;
import janggi.point.InitialPoint;
import janggi.point.Point;
import janggi.game.Team;
import janggi.point.PointDistance;
import janggi.point.Route;
import java.util.ArrayList;
import java.util.List;

public class Sang implements Movable {

    private static final String NAME = "상";
    private static final List<Sang> sangs;

    private final Team team;
    private final Point point;

    public Sang(Team team, Point point) {
        this.team = team;
        this.point = point;
    }

    static {
        List<Sang> createdPieces = new ArrayList<>();
        for (Point point : InitialPoint.SANG.getRedPoints()) {
            createdPieces.add(new Sang(Team.HAN, point));
        }
        for (Point point : InitialPoint.SANG.getBluePoints()) {
            createdPieces.add(new Sang(Team.CHO, point));
        }
        sangs = createdPieces;
    }

    public static List<Sang> values() {
        return new ArrayList<>(sangs);
    }

    @Override
    public boolean canMove(Point targetPoint, Hurdles hurdles) {
        if (isDistanceOverFlow(targetPoint)) {
            return false;
        }
        List<Direction> directions = Direction.oneCardinalAndDiagonalFrom(
                this.point, targetPoint, 3, 2
        );

        //장애물 체크
        return isRouteHaveNoHurdle(targetPoint, hurdles, directions);
    }

    private boolean isDistanceOverFlow(Point targetPoint) {
        PointDistance distance = PointDistance.calculate(point, targetPoint);
        if (!distance.isSameWith(Math.sqrt(13))) {
            return true;
        }
        return false;
    }

    private boolean isRouteHaveNoHurdle(Point targetPoint, Hurdles hurdles, List<Direction> directions) {
        Route route = Route.follow(directions, this.point);
        if (route.isCrashExists(hurdles)) {
            Crashes crashes = route.findCrashes(hurdles, this);
            return crashes.hasNoCrashes(this.team, targetPoint, hurdles);
        }
        return true;
    }

    @Override
    public Movable updatePoint(Point afterPoint) {
        return new Sang(team, afterPoint);
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
