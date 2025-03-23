package janggi.piece;

import janggi.point.Crashes;
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
    public boolean isInMovingRange(Point targetPoint, Hurdles hurdles) {
        PointDistance distance = PointDistance.calculate(point, targetPoint);
        if (!distance.isSameWith(Math.sqrt(5))) {
            return false;
        }
        List<Direction> directions = Direction.oneCardinalAndDiagonalFrom(
                this.point, targetPoint, 3, 2
        );

        //장애물 체크
        Route route = Route.follow(directions, this.point);
        if (route.isCrashExists(hurdles)) {
            Crashes crashPoints = route.findCrashes(hurdles);
            return crashPoints.isPreyOnly(this.team, targetPoint, hurdles);
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
