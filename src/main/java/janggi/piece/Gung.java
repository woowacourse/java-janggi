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
