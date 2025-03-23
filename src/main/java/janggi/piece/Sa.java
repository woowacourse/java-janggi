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

public class Sa implements Movable {

    private static final String NAME = "사";
    private static final List<Sa> sas;

    private final Team team;
    private final Point point;

    public Sa(Team team, Point point) {
        this.team = team;
        this.point = point;
    }

    static {
        List<Sa> createdPieces = new ArrayList<>();
        for (Point point : InitialPoint.SA.getRedPoints()) {
            createdPieces.add(new Sa(Team.HAN, point));
        }
        for (Point point : InitialPoint.SA.getBluePoints()) {
            createdPieces.add(new Sa(Team.CHO, point));
        }
        sas = createdPieces;
    }

    public static List<Sa> values() {
        return new ArrayList<>(sas);
    }

    @Override
    public boolean isInMovingRange(Point targetPoint, Hurdles hurdles) {
        PointDistance distance = PointDistance.calculate(point, targetPoint);
        if (!distance.isSameWith(1) && !distance.isSameWith(Math.sqrt(2))) {
            return false;
        }
        Direction direction = Direction.cardinalOrDiagonalFrom(this.point, targetPoint);

        //장애물 체크
        Route route = Route.repeat(direction, this.point, targetPoint);
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
